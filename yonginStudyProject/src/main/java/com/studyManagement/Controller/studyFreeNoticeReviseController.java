package com.studyManagement.Controller;

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
import com.studyManagement.Service.studyManagementService;
import com.notice.VO.boardVO;
import com.notice.Validator.boardValidator;

@Controller
public class studyFreeNoticeReviseController {

	@Resource(name="studyManagementService")
	private studyManagementService studyManagementService;
	
	@Resource(name="fileService")
	private fileService fileService;
	
	@RequestMapping(value = "/studyManagemet/studyFreeNoticeRevise.do", method = RequestMethod.GET)
	public String studyFreeNoticeRevise(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("boardVO", new boardVO());
		
		return "jsp/studyManagement/studyFreeNoticeRevise"; 
	}
	
	/**
	 * �ش� ���� ��ȸ
	 * @param systemNoticeCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/selectStudyFreeNoticeRevise.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyInfoDetail(@RequestBody String boardCode) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(boardCode == null || boardCode.equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		boardVO boardInfo = studyManagementService.selectStudyFreeNoticeInfoDetail(boardCode);
		
		if(boardInfo == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		List<Map<String, Object>> fileList = fileService.selectFileList(boardCode);
		
		mReturn.put("fileList", fileList);
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ��ȸ�Ͽ����ϴ�.");
		mReturn.put("boardInfo", boardInfo);
		
		return mReturn;
	}
	
	/**
	 * ���͵� �����Խ��� ����
	 * @param boardVO
	 * @param bingdingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/studyFreeNoticeRevise", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerAjaxFunction(boardVO boardVO, HttpSession session, BindingResult bindingResult, MultipartHttpServletRequest mpRequest) throws Exception {
	      
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
		studyManagementService.reviseStudyFreeNotice(boardVO, mpRequest);
		mReturn.put("result", "success");
		mReturn.put("message", "������ �Ϸ�Ǿ����ϴ�.");
		
		return mReturn;
	}
}
