package com.message.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.message.Service.messageService;
import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;
import com.main.VO.studyInfoVO;
import com.message.VO.messageInfoVO;

@Controller
public class sendMessageListController {

	@Resource(name="messageService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private messageService messageService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(messageListController.class);
	
	/**
	 * ������ Mapping
	 */
	@RequestMapping(value = "/sendMessageList.do", method = RequestMethod.GET)
	public String MoreStudyForm(Locale locale) {
		 
		return "jsp/message/sendMessageList";
	}
}
