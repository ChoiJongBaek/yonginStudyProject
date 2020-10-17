package com.studyManagement.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.login.VO.userInfoVO;

@Controller
public class studyInfoChangeController {

	/**
	 * ���͵� ���� ���� Mapping
	 */
	@RequestMapping(value = "/studyManagemet/studyInfoChange.do", method = RequestMethod.GET)
	public String studyInfoChange(HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		return "jsp/studyManagement/studyInfoChange"; 
	}
}
