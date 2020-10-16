package com.studyManagement.Controller; 

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.commonCode.Service.commonCodeService;
import com.login.VO.userInfoVO;
import com.studyManagement.Service.studyManagementService;

@Controller
public class studyCalendarController {
	@Resource(name="studyManagementService") // 해당 서비스가 리소스임을 표시합니다.
	private studyManagementService studyManagementService;

	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	
	
	@RequestMapping(value = "/studyManagement/calendarPopup.do", method = RequestMethod.POST)
	public String calendarDetailPopup(HttpSession session) throws Exception {
		/** 세션에 유저가 정상적으로 등록되어 있지 않다면 로그인 페이지 로 이동(시작) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** 세션에 유저가 정상적으로 등록되어 있지 않다면 로그인 페이지로 이동(끝) **/
		return "jsp/studyManagement/calendarPopup";
	}
	
}
 