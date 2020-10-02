package com.qna.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.login.VO.userInfoVO;
import com.notice.VO.boardVO;
import com.notice.Validator.boardValidator;
import com.qna.Service.qnaService;

@Controller
public class writeQnaController {
	@Resource(name="qnaService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private qnaService qnaService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(writeQnaController.class);
	
	/**
	 * QnA �ۼ� �˾�
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/systemQna/writeQna.do", method = RequestMethod.GET)
	public String writeQna(Model model, HttpSession session) {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		return "jsp/qna/writeQna";
	}
	
	/**
	 * Qna �ۼ�
	 * @param boardVO
	 * @param bingdingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/systemQna/makeQna", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> makeQna(boardVO boardVO, HttpSession session, BindingResult bindingResult, MultipartHttpServletRequest mpRequest) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
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
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		boardVO.setRgstusId(user.getUserCode());
		boardVO.setQnaStatus("10");	
		
		qnaService.insertQna(boardVO, mpRequest);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� �����Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	
}
