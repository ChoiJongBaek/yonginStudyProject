package com.message.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.login.VO.userInfoVO;
import com.message.Service.messageService;
import com.message.VO.messageInfoVO;
import com.message.Validator.sendMessageValidator;

@Controller
public class sendMessageController {

	
	@Resource(name="messageService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private messageService messageService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(messageListController.class);
	
	/**
	 * ���� ������ Mapping
	 */
	@RequestMapping(value = "/sendMessage.do", method = RequestMethod.GET)
	public String sendMessageForm(HttpSession session) {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
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
	public Map<String, Object> sendDeleteAjaxFunction(@RequestBody messageInfoVO messageInfoVO, HttpSession session, BindingResult bindingResult) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		messageInfoVO.setUserCodeFrom(user.getUserCode());
		messageInfoVO.getUserCodeFrom();
		
		/** ������ ����(����) **/
		sendMessageValidator sendMessageValidator = new sendMessageValidator();
		sendMessageValidator.validate(messageInfoVO, bindingResult);
		
		// ���� ���� �� ���� �޽����� �Բ� ����
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMsg = "";
		    for (FieldError error : errors ) {
		    	errorMsg += error.getDefaultMessage() + "\n";
		    }

		    mReturn.put("result", "fail");
			mReturn.put("message", errorMsg);
			
			return mReturn;
		}  
		/** ������ ����(��) **/
		
		int count = messageService.selectUserExistCount(messageInfoVO.getUserCodeTo());
		
		if(count != 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "�޴� ��� ID�� �������� �ʽ��ϴ�.\n�ٽ� �Է����ּ���.");
			
			return mReturn;
		}
		messageService.sendMessage(messageInfoVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���۵Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	@RequestMapping(value = "/message/replyMessageForm.do", method = RequestMethod.POST)
	public String replyMessageForm(HttpSession session) {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		return "jsp/message/replyMessage";
	}
}
