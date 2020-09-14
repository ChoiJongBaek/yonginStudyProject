package com.message.Controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.login.VO.userInfoVO;
import com.message.Service.messageService;
import com.message.VO.messageInfoVO;

@Controller
public class sendMessageController {

	
	@Resource(name="messageService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private messageService messageService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(messageListController.class);
	
	/**
	 * ���� ������ Mapping
	 */
	@RequestMapping(value = "/sendMessage.do", method = RequestMethod.GET)
	public String MoreStudyForm() {
		
		return "jsp/message/sendMessage";
	}
	
	/** 
	 * ���� ������
	 * @param messageInfoVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/sendMessage.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendDeleteAjaxFunction(@RequestBody messageInfoVO messageInfoVO, HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		messageInfoVO.setUserCodeFrom(user.getUserCode());
		messageInfoVO.getUserCodeFrom();
		
		//���������� �߰�
		messageService.sendMessage(messageInfoVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���۵Ǿ����ϴ�.");
		
		return mReturn;
	}
}
