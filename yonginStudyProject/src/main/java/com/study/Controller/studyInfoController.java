package com.study.Controller;

import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.login.Controller.registerMemeberController;
import com.login.Service.loginService;

@Controller
public class studyInfoController {
	@Resource(name="loginService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private loginService loginService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(studyInfoController.class);
	
	/**
	 * ���͵����� �˾� Mapping
	 */
	@RequestMapping(value = "/study/studyInfo.do", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {

		return "jsp/study/studyInfo";
	}
}
