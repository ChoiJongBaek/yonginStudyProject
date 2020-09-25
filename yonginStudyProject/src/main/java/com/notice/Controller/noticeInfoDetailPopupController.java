package com.notice.Controller;

import java.util.HashMap;
import java.util.List;
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
import com.notice.VO.moreNoticeInfoVO;
import com.notice.Service.systemNoticeService;

@Controller
public class noticeInfoDetailPopupController {

	@Resource(name="systemNoticeService")
	private systemNoticeService systemNoticeService;
	
	@RequestMapping(value = "/notice/noticeInfoDetailPopup.do", method = RequestMethod.POST)
	public String studyInfoDetailPopup(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("moreNoticeInfoVO", new moreNoticeInfoVO());
		
		return "jsp/notice/noticeInfoDetailPopup"; 
	}
	
	@RequestMapping(value="/notice/selectNoticeInfoDetail.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyInfoDetail(@RequestBody String systemNoticeCode) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(systemNoticeCode == null || systemNoticeCode.equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		moreNoticeInfoVO systemNoticeInfo = systemNoticeService.selectSystemNoticeInfoDetail(systemNoticeCode);
		
		if(systemNoticeInfo == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ��ȸ�Ͽ����ϴ�.");
		mReturn.put("systemNoticeInfo", systemNoticeInfo);
		
		return mReturn;
	}
}