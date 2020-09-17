package com.message.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.login.VO.userInfoVO;
import com.message.Service.messageService;
import com.message.VO.messageInfoVO;

@Controller
public class messageInfoDetailPopupController {
	
	@Resource(name="messageService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private messageService messageService;
	
	/**
	 * ���� �� �˾� 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/message/messageInfoDetailPopup.do", method = RequestMethod.POST)
	public String messageInfoDetailPopup(HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		return "jsp/message/messageInfoDetailPopup"; 
	}
	
	
	/**
	 * ���� �� �˾�
	 * @param messageCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/message/selectMessageInfoDetail.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectMessageInfoDetail(@RequestBody String messageCode) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(messageCode == null || messageCode.equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		messageInfoVO messageInfo = messageService.selectMessageInfoDetail(messageCode);
		
		if(messageInfo == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ��ȸ�Ͽ����ϴ�.");
		mReturn.put("messageInfo", messageInfo);
		
		return mReturn;
	}
}
