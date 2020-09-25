package com.login.Controller;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;
import com.login.Service.loginService;
import com.login.VO.userInfoVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class loginController {
	@Resource(name="loginService") 
	private loginService loginService;	// ���� ���� �߰�
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	@Inject
    PasswordEncoder passwordEncoder;	// ��ȣȭ ��� �߰�
	
	private static final Logger logger = LoggerFactory.getLogger(loginController.class);
	
	/**
	 * �α��� ȭ�� Controller
	 * @throws Exception 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model model, HttpSession session) throws Exception {
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user != null) {
			List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("studyTopic");
			
			//model ������ �����͸� ��� jsp�� ����
			model.addAttribute("studyTopicArray", codeResult);
			
			return "jsp/main/main";
		}
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
	public String login(userInfoVO userInfoVO, HttpServletRequest req, RedirectAttributes rttr, Model model) throws Exception{
		logger.info("post login");
		
		HttpSession session = req.getSession();
		
		// ID�� PW�� ����ִ� ���
		if(userInfoVO.getUserId().equals("") && userInfoVO.getUserPw().equals("")) {
			session.setAttribute("user", null);
			rttr.addFlashAttribute("msg", "ID�� PW�� �Է����ּ���.");
			return "redirect:/";
		}
		else if(userInfoVO.getUserId().equals("")) {
			session.setAttribute("user", null);
			rttr.addFlashAttribute("msg", "ID��  �Է����ּ���.");
			return "redirect:/";
		}
		else if(userInfoVO.getUserPw().equals("")) {
			session.setAttribute("user", null);
			rttr.addFlashAttribute("msg", "PW��  �Է����ּ���.");
			return "redirect:/";
		}
		
		// �Է��� ID�� PW�� �̿��� select 
		userInfoVO login = loginService.login(userInfoVO);
		
		// ID�� PW�� �ٸ� ���
		if(login == null || !(passwordEncoder.matches(userInfoVO.getUserPw(), login.getUserPw()))) {
			session.setAttribute("user", null);
			rttr.addFlashAttribute("msg", "ID �Ǵ� PW�� Ȯ�����ּ���.");
			return "redirect:/";
		// ID�� PW�� ���������� ������ ���
		}else {
			login.setUserPw("");
			session.setAttribute("user", login);
			
			List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("studyTopic");
			List<commonCodeVO> codeResult2 = commonCodeService.selectCommonCodeList("calendarType");
			
			//model ������ �����͸� ��� jsp�� ����
			model.addAttribute("studyTopicArray", codeResult);
			model.addAttribute("calendarType", codeResult2);
			
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
