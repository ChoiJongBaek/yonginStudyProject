package com.main.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.commonCode.Service.commonCodeService;
import com.login.VO.userInfoVO;
import com.main.Service.adminService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class adminPageController {
	@Resource(name="adminService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private adminService adminService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	private static final Logger logger = LoggerFactory.getLogger(adminPageController.class);
	
	/**
	 * ���������� �˾� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/openAdminPage.do", method = RequestMethod.GET)
	public String openAdminPage(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		
		// ������ ������ ���� ��� �α��� �������� ����(���� �������� �߰�)
		if(!user.getUserIsAdmin().equals("Y")) {
			session.invalidate();
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		return "jsp/main/adminPage";
	}
}
