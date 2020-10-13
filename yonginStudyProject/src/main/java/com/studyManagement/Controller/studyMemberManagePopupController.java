package com.studyManagement.Controller; 

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;
import com.login.VO.userInfoVO;
import com.studyManagement.Service.studyManagementService;

@Controller
public class studyMemberManagePopupController {
	@Resource(name="studyManagementService") // 해당 서비스가 리소스임을 표시합니다.
	private studyManagementService studyManagementService;

	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	/**
	 * 스터디 전용 페이지 Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/studyManagement/studyMemberManagePopup.do", method = RequestMethod.GET)
	public String studyMainForm(Model model, HttpSession session) throws Exception {
		/** 세션에 유저가 정상적으로 등록되어 있지 않다면 로그인 페이지로 이동(시작) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		
		/** 세션에 유저가 정상적으로 등록되어 있지 않다면 로그인 페이지로 이동(끝) **/
		
		
		List<commonCodeVO> codeResult1 = commonCodeService.selectCommonCodeList("SAFStatus");
		List<commonCodeVO> codeResult2 = commonCodeService.selectCommonCodeList("studyTopic");
		List<commonCodeVO> codeResult3 = commonCodeService.selectCommonCodeList("studyAuthority");
		
		model.addAttribute("applicationFormStatusArray", codeResult1);
		model.addAttribute("studyTopicArray", codeResult2);
		model.addAttribute("studyAuthorityArray", codeResult3);
		
		return "jsp/studyManagement/studyMemberManagePopup";
	}
}
