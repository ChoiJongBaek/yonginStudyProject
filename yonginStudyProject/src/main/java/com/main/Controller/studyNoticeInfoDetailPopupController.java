package com.main.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.login.VO.userInfoVO;
import com.main.Service.mainService;
import com.main.VO.studyNoticeInfoVO;

@Controller
public class studyNoticeInfoDetailPopupController {

	@Resource(name="mainService")
	private mainService mainService;
	
	@RequestMapping(value = "/main/studyNoticeInfoDetailPopup.do", method = RequestMethod.POST)
	public String studyNoticeInfoDetailPopup(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("studyNoticeInfoVO", new studyNoticeInfoVO());
		
		return "jsp/main/studyNoticeInfoDetailPopup"; 
	}
	
	/**
	 * �������� �� ���� 
	 * @param systemNoticeCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/main/selectStudyNoticeInfoDetail.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyNoticeInfoDetail(@RequestBody String studyNoticeCode) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(studyNoticeCode == null || studyNoticeCode.equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		mainService.updateStudyNoticeCnt(studyNoticeCode);
		studyNoticeInfoVO studyNoticeInfo = mainService.selectStudyNoticeInfoDetail(studyNoticeCode);
		
		if(studyNoticeInfo == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ��ȸ�Ͽ����ϴ�.");
		mReturn.put("studyNoticeInfo", studyNoticeInfo);
		
		return mReturn;
	}
}
