package com.main.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commonCode.Service.commonCodeService;
import com.commonFunction.Controller.yonginFunction;
import com.login.Service.loginService;
import com.login.VO.userInfoVO;
import com.login.Validator.pwChangeValidator;
import com.main.Service.myPageService;
import com.main.VO.studyInfoVO;
import com.main.Validator.changeUserInfoValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class myPageFormController {
	@Resource(name="myPageService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private myPageService myPageService;
	
	@Autowired
	changeUserInfoValidator changeUserInfoValidator;// validator ���� �ҷ���
	
	@Resource(name="loginService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private loginService loginService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	@Inject
    PasswordEncoder passwordEncoder;	// ��ȣȭ ��� �߰�
	
	private static final Logger logger = LoggerFactory.getLogger(myPageFormController.class);
	
	/**
	 * ���������� �˾� Mapping
	 */
	@RequestMapping(value = "/myPage.do", method = RequestMethod.GET)
	public String myPageForm(Model model, HttpSession session) {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		
		String userCode = user.getUserCode();
		userInfoVO userInfoVO = myPageService.selectUserInfoData(userCode);
		model.addAttribute("userInfoVO", new userInfoVO());
		model.addAttribute("currentUser", userInfoVO);
		return "jsp/main/mypage";
	}

	/**
	 * ����� ���� ����
	 * @param userInfoVO
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/changeUserInfo.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeUserInfo(@ModelAttribute("userInfoVO") userInfoVO userInfoVO, BindingResult bindingResult, HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		/** ������ ����(����) **/
		changeUserInfoValidator changeUserInfoValidator = new changeUserInfoValidator();
		changeUserInfoValidator.validate(userInfoVO, bindingResult);
		
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
		
		userInfoVO.setUserCode(user.getUserCode());
		
		// ��ȭ��ȣ���� '-' ����
		String removePhoneNumber = yonginFunction.remove(userInfoVO.getUserPhoneNumber(), '-');	//com.commonFunction.Controller�� �ִ� ���� �Լ��� �̿��� ���� ����
		userInfoVO.setUserPhoneNumber(removePhoneNumber);
		
		myPageService.updateUserInfo(userInfoVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� �����Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ���������� ��й�ȣ ���� ������
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/myPageChangePwForm.do", method = RequestMethod.GET)
	public String myPageChangePwForm(Model model, HttpSession session) {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		model.addAttribute("userInfoVO", new userInfoVO());
		
		return "jsp/main/myPageChangePw";
	}
	
	/**
	 * ��й�ȣ ����
	 * @param userInfoVO
	 * @param session
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/myPageChangePw.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> myPageChangePw(@ModelAttribute("userInfoVO") userInfoVO userInfoVO, HttpSession session, BindingResult bindingResult) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();

		/** ������ ����(����) **/
		pwChangeValidator pwChangeValidator = new pwChangeValidator();
		pwChangeValidator.validate(userInfoVO, bindingResult);
		
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
		
		// ��й�ȣ ã�⿡�� ���ǿ� ���� ����� ���� ������
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
		if(user == null) {
			mReturn.put("result", "fail");
			mReturn.put("message", "�߸��� �����Դϴ�.");
			System.out.println("�������������");
			return mReturn;
		}
		
		userInfoVO.setUserCode(user.getUserCode());

		// ��й�ȣ ��ȣȭ
		userInfoVO.setUserPw(passwordEncoder.encode(userInfoVO.getUserPw()));
				
		// ��й�ȣ ������Ʈ
		loginService.updatePw(userInfoVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "��й�ȣ�� ����Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ���� ���罺�͵� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/myPage/selectStudyMadeByMeList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyMadeByMeList(HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		String userCode = user.getUserCode();
		
	      
		List<studyInfoVO> ltResult = myPageService.selectStudyMadeByMeList(userCode);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���͵� ����� �����ϴ�.");
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ �����Ͽ����ϴ�.");
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
	
	/**
	 * ���� ���Ե� ���͵� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/myPage/selectParticipateStudyList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectParticipateStudyList(HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		String userCode = user.getUserCode();
		
	      
		List<studyInfoVO> ltResult = myPageService.selectParticipateStudyList(userCode);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���͵� ����� �����ϴ�.");
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ �����Ͽ����ϴ�.");
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
}
