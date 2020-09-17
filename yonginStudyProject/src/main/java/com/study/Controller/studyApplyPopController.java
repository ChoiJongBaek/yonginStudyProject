package com.study.Controller;

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
import com.study.Service.studyService;
import com.study.VO.studyApplicationFormUserVO;
import com.study.Validator.studyApplicationFormUserValidator;
import com.study.Validator.updateStudyApplicationFormUserValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class studyApplyPopController {
	@Resource(name="studyService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyService studyService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(studyApplyPopController.class);
	
	/**
	 * ���͵� ��û�ϱ� �� 
	 */
	@RequestMapping(value = "/studyApplyPop.do", method = RequestMethod.POST)
	public String studyApplyPopForm(HttpSession session) {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		   
		return "jsp/study/studyApplyPop";
	}
	
	/** 
	 * ���͵� ��û Insert
	 * @param messageInfoVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/study/applyStudy.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> applyStudy(@RequestBody studyApplicationFormUserVO studyApplicationFormUserVO, HttpSession session, BindingResult bindingResult) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		
		/** ������ ����(����) **/
		studyApplicationFormUserValidator studyApplicationFormUserValidator = new studyApplicationFormUserValidator();
		studyApplicationFormUserValidator.validate(studyApplicationFormUserVO, bindingResult);
		
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
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
		studyApplicationFormUserVO.setUserCode(user.getUserCode());
		
		int count = studyService.selectStudyApplicationFormCount(studyApplicationFormUserVO);
		
		if(count != 0) {
			mReturn.put("result", "fail");
			mReturn.put("message", "�ش� ���͵� �̹� ��û�ϼ̽��ϴ�.");
			
			return mReturn;
		}
		
		studyService.insertStudyApplicationFormUser(studyApplicationFormUserVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "��û�� �Ϸ�Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ��û�� ����
	 * @param studyApplicationFormUserVO
	 * @param session
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/study/updateStudyApplicationForm.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStudyApplicationForm(@RequestBody studyApplicationFormUserVO studyApplicationFormUserVO, HttpSession session, BindingResult bindingResult) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		
		/** ������ ����(����) **/
		updateStudyApplicationFormUserValidator updateStudyApplicationFormUserValidator = new updateStudyApplicationFormUserValidator();
		updateStudyApplicationFormUserValidator.validate(studyApplicationFormUserVO, bindingResult);
		
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
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
		studyApplicationFormUserVO.setUserCode(user.getUserCode());
		
		studyService.updateStudyApplicationFormUser(studyApplicationFormUserVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "������ �Ϸ�Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	@RequestMapping(value="/study/selectStudyApplicationForm.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyApplicationForm(@RequestBody studyApplicationFormUserVO studyApplicationFormUserVO, HttpSession session, BindingResult bindingResult) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(studyApplicationFormUserVO.getStudyCode().equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���͵� ���õ��� �ʾҽ��ϴ�.");
			
			return mReturn;
		}
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
		studyApplicationFormUserVO.setUserCode(user.getUserCode());
		
		studyApplicationFormUserVO resultVo = studyService.selectStudyApplicationForm(studyApplicationFormUserVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "������ �Ϸ�Ǿ����ϴ�.");
		mReturn.put("resultVO", resultVo);
		
		return mReturn;
	}
}

