package com.message.Controller;

import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.login.Service.loginService;

public class checkMessageController {

	@Resource(name="loginService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private loginService loginService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(messageListController.class);
	
	/**
	 * ������ Mapping
	 */
	@RequestMapping(value = "/checkMessage.do", method = RequestMethod.GET)
	public String MoreStudyForm(Locale locale) {
		 
		return "jsp/message/checkMessage";
	}
	
}
