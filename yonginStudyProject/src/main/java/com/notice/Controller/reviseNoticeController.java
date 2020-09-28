package com.notice.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.commonFunction.Service.fileService;
import com.login.VO.userInfoVO;
import com.notice.Service.systemNoticeService;
import com.notice.VO.moreNoticeInfoVO;
import com.notice.Validator.systemNoticeValidator;

@Controller
public class reviseNoticeController {
	
	@Resource(name="systemNoticeService")
	private systemNoticeService systemNoticeService;
	
	@Resource(name="fileService")
	private fileService fileService;
	
	@RequestMapping(value = "/notice/reviseNotice.do", method = RequestMethod.GET)
	public String studyInfoDetailPopup(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("moreNoticeInfoVO", new moreNoticeInfoVO());
		
		return "jsp/notice/reviseNotice"; 
	}
	
	/**
	 * �ش� ���� ��ȸ
	 * @param systemNoticeCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/notice/selectReviseSystemNotice.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyInfoDetail(@RequestBody String systemNoticeCode) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(systemNoticeCode == null || systemNoticeCode.equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		moreNoticeInfoVO systemNoticeInfo = systemNoticeService.selectSystemNoticeInfoDetail(systemNoticeCode);
		
		if(systemNoticeInfo == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		List<Map<String, Object>> fileList = fileService.selectFileList(systemNoticeCode);
		
		mReturn.put("fileList", fileList);
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ��ȸ�Ͽ����ϴ�.");
		mReturn.put("systemNoticeInfo", systemNoticeInfo);
		
		return mReturn;
	}
	
	/**
	 * �������� ����
	 * @param moreNoticeInfoVO
	 * @param bingdingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/notice/reviseSystemNotice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerAjaxFunction(moreNoticeInfoVO moreNoticeInfoVO, HttpSession session, BindingResult bindingResult, MultipartHttpServletRequest mpRequest) throws Exception {
	      
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
		systemNoticeService.reviseSystemNotice(moreNoticeInfoVO, mpRequest);
		mReturn.put("result", "success");
		mReturn.put("message", "������ �Ϸ�Ǿ����ϴ�.");
		
		return mReturn;
	}
	
}
