package com.login.Controller;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.login.Service.loginService;
import com.login.VO.userInfoVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class loginController {
	@Resource(name="loginService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private loginService loginService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(loginController.class);
	
	/**
	 * �α��� ȭ�� Controller
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {

		return "jsp/login/login";
	}
	
	/**
	 * �α��� ���
	 * @param userInfoVO
	 * @param req
	 * @param rttr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login.json", method = RequestMethod.POST)
	public String login(userInfoVO userInfoVO, HttpServletRequest req, RedirectAttributes rttr) throws Exception{
		logger.info("post login");
		
		HttpSession session = req.getSession();
		
		// ID�� PW�� ����ִ� ���
		if(userInfoVO.getUserId().equals("") || userInfoVO.getUserPw().equals("")) {
			session.setAttribute("user", null);
			rttr.addFlashAttribute("msg", "ID�� PW�� �Է����ּ���.");
			return "redirect:/";
		}
		
		// �Է��� ID�� PW�� �̿��� select 
		userInfoVO login = loginService.login(userInfoVO);
		
		// ����� ���������ʴ� ���
		if(login == null) {
			session.setAttribute("user", null);
			rttr.addFlashAttribute("msg", "ID �Ǵ� PW�� Ȯ�����ּ���.");
			return "redirect:/";
			
		//����� �����ϴ� ���
		}else {
			session.setAttribute("user", login);
			return "jsp/main/main";
		}
	}
	
	/**
	 * �α׾ƿ� ���
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout.json", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception{
		
		session.invalidate();
		
		return "redirect:/";
	}
	
}
