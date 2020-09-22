package com.notice.Controller;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.login.VO.userInfoVO;
import com.main.VO.studyInfoVO;
import com.message.Validator.sendMessageValidator;
import com.notice.Service.systemNoticeService;
import com.notice.VO.moreNoticeInfoVO;
import com.notice.Validator.systemNoticeValidator;
import com.study.Validator.studyInfoValidator;

@Controller
public class writeNoticeController {
	@Resource(name="systemNoticeService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private systemNoticeService systemNoticeService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(writeNoticeController.class);
	
	/**
	 * �������� �ۼ� Mapping
	 */
	@RequestMapping(value = "/writeNotice.do", method = RequestMethod.GET)
	public String MoreNoticeForm(HttpSession session) {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		 
		return "jsp/notice/writeNotice";
	}
	
	/**
	 * �������� �ۼ�
	 * @param moreNoticeInfoVO
	 * @param bingdingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/notice/makeSystemNotice.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerAjaxFunction(@RequestBody moreNoticeInfoVO moreNoticeInfoVO, HttpSession session, BindingResult bindingResult) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		moreNoticeInfoVO.setSystemNoticeRgstusId(user.getUserCode());
		
		/** ������ ����(����) **/
		systemNoticeValidator systemNoticeValidator = new systemNoticeValidator();
		systemNoticeValidator.validate(moreNoticeInfoVO, bindingResult);
		
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
	
		systemNoticeService.insertSystemNotice(moreNoticeInfoVO);
		mReturn.put("result", "success");
		mReturn.put("message", "���������� �����Ǿ����ϴ�.");
		
		return mReturn;
	}
}
