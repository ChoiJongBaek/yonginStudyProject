package com.login.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;
import com.commonFunction.Controller.yonginFunction;
import com.login.Service.loginService;
import com.login.VO.userInfoVO;
import com.login.Validator.userInfoValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class registerMemeberController {
	@Resource(name="loginService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private loginService loginService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	@Inject
    PasswordEncoder passwordEncoder;	// ��ȣȭ ��� �߰�
	
	@Autowired
	userInfoValidator userInfoValidator;// validator ���� �ҷ���
	
	
	private static final Logger logger = LoggerFactory.getLogger(registerMemeberController.class);
	
	/**
	 * ȸ������ �˾� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/registerForm.do", method = RequestMethod.GET)
	public String registerForm(Model model) throws Exception {
		List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("pwHint");
		
		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("pwHint", codeResult);
		model.addAttribute("userInfoVO", new userInfoVO());
		return "jsp/login/registerMember";
	}
	
	/**
	 * ȸ������
	 * @param userInfoVO
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/registerMember.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerAjaxFunction(@ModelAttribute("userInfoVO") userInfoVO userInfoVO, BindingResult bindingResult) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		/** ������ ����(����) **/
		/*userInfoValidator userInfoValidator = new userInfoValidator();
		userInfoValidator.validate(userInfoVO, bindingResult);
		
		// ���� ���� �� ���� �޽����� �Բ� ����
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMsg = "";
		    for (FieldError error : errors ) {
		    	errorMsg = error.getDefaultMessage() + "\n";
		    }

		    mReturn.put("result", "fail");
			mReturn.put("message", errorMsg);
			
			return mReturn;
		}  
		/** ������ ����(��) **/
		
		//��й�ȣ ��ȣȭ
		userInfoVO.setUserPw(passwordEncoder.encode(userInfoVO.getUserPw()));
		
		// ��¥���� '-' ����
		String removeEmail = yonginFunction.remove(userInfoVO.getUserBirth(), '-');	//com.commonFunction.Controller�� �ִ� ���� �Լ��� �̿��� ���� ����
		userInfoVO.setUserBirth(removeEmail);
		
		loginService.insertMember(userInfoVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ���ԵǾ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ID�ߺ�üũ
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/register/checkExsitingId.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkExsitingId(@RequestBody String userId) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		// id �Է� Ȯ��
		if(userId == null || userId.equals("")){
			mReturn.put("result", "fail");
			mReturn.put("message", "���̵� �Է����ּ���.");
			
			return mReturn;
		}
		
		// ��, �� ���� ����
		userId = userId.trim();
		
		// ���� ID�� ����ϴ� �������� ���� ��ȯ
		int count = loginService.selectSameId(userId);
		
		if(count == 0) {
			mReturn.put("result", "success");
			mReturn.put("message", "�� ���̵� ����Ͻ� �� �ֽ��ϴ�.");
			
			return mReturn;
		}
		else {
			mReturn.put("result", "fail");
			mReturn.put("message", "�� ���̵� �̹� ������Դϴ�.");
			
			return mReturn;
		}
	}
	
	
}
