package com.studyManagement.Controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.login.VO.userInfoVO;
import com.notice.VO.boardVO;
import com.notice.Validator.boardValidator;
import com.studyManagement.Service.studyNoticeService;

@Controller
public class writeStudyNoticeController {
	@Resource(name="studyNoticeService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyNoticeService studyNoticeService;
	
	private static final Logger logger = LoggerFactory.getLogger(writeStudyNoticeController.class);
	
	/**
	 * ���͵� �������� �ۼ� Mapping
	 */
	@RequestMapping(value = "/writeStudyNotice.do", method = RequestMethod.GET)
	public String studyNoticeForm(HttpSession session) {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		// ������ ������ ���� ��� �α��� �������� ����(������ ������ �ʿ��� ���)
		if(!user.getUserIsAdmin().equals("Y")) {
			session.invalidate();
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		 
		return "jsp/studyManagement/writeStudyNotice";
	}

	/**
	 * ���͵� �������� �ۼ�
	 * @param boardVO
	 * @param bingdingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/makeStudyNotice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> makeStudyNotice(boardVO boardVO, HttpSession session, BindingResult bindingResult, MultipartHttpServletRequest mpRequest) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		boardVO.setRgstusId(user.getUserCode());

		// ������ ������ ���� ��� ���� �޽��� �߻�
		if(!user.getUserIsAdmin().equals("Y")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "������ �����ϴ�.");
			
			return mReturn;
		}
				
		/** ������ ����(����) **/
		boardValidator boardValidator = new boardValidator();
		boardValidator.validate(boardVO, bindingResult);
		
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
		studyNoticeService.insertStudyNotice(boardVO, mpRequest);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� �����Ǿ����ϴ�.");
		
		return mReturn;
	}
}

