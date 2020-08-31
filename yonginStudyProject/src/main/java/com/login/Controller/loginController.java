package com.login.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.login.Service.loginService;
import com.login.Service.testService;
import com.login.VO.testVO;
import com.login.VO.userInfoVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class loginController {
	@Resource(name="testService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private testService testService;
	
	@Resource(name="loginService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private loginService loginService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(loginController.class);
	
	/**
	 * �α��� ȭ�� Controller
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String aa = testService.aa();
		List<testVO> testList = testService.selectlistService();
		
		System.out.println("TEST  : "+aa);
		String aaaa = testList.get(0).getColumn1();
		
		model.addAttribute("testList", aaaa);
		return "jsp/login/login";
	}
	
	/**
	 * ȸ������ �˾� Mapping
	 */
	@RequestMapping(value = "/login/registerForm.do", method = RequestMethod.GET)
	public String registerForm() {
		
		return "jsp/login/registerMember";
	}
	
	@RequestMapping(value="/login/testAjax.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> testAjaxFunction(@RequestBody String data) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		// ���� RequestBody �κ����� JS���� ������ �����͸� ǥ�� �ٵ� js���� ���� �����Ͱ� 1�̶�°� ��Ȯ�� ���Ȳ���� js���� ���� �Ѱ��شٴ°��� ?����
		// js���� ���� �����Ͱ� "1"�� �� DB���� �� ������
		if(data.equals("1")) {
			List<testVO> testList = testService.selectlistService();
			
			mReturn.put("resultList", testList);
			mReturn.put("message", "����");
		    mReturn.put("result", "success");   
		}
		else {
			mReturn.put("resultList", null);
			mReturn.put("message", "����");
		    mReturn.put("result", "fail");   
		}
	    
	    return mReturn;
	}
	
	@RequestMapping(value="/registerMember/registerAjax.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerAjaxFunction(@RequestBody userInfoVO data) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		loginService.insertMember(data);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���忡 �����߽��ϴ�.");
		
		return mReturn;
	}
}
