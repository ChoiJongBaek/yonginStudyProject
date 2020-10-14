package com.studyManagement.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.login.VO.userInfoVO;
import com.studyManagement.Service.studyManagementService;

@Controller
public class writeStudyNoticeController {
	@Resource(name="studyManagementService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyManagementService studyManagementService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(writeStudyNoticeController.class);
	
	/**
	 * ���͵� �������� �ۼ� Mapping
	 */
	@RequestMapping(value = "/writeStudyNotice.do", method = RequestMethod.GET)
	public String openWriteStudyNotice(HttpSession session) {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		// ������ ������ ���� ��� �α��� �������� ����(������ ������ �ʿ��� ���)
		if(!user.getUserIsAdmin().equals("Y")) {
			session.invalidate();
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		 
		return "jsp/studyManagement/writeStudyNotice";
	}

}
