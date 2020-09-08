package com.study.Controller;

import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.study.Service.studyService;
import com.main.VO.studyInfoVO;

@Controller
public class studyInfoController {
	@Resource(name="studyService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyService studyService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(studyInfoController.class);
	
	/**
	 * ���͵����� �˾� Mapping
	 */
	@RequestMapping(value = "/studyInfo.do", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {

		return "jsp/study/studyInfo"; 
	}
}
