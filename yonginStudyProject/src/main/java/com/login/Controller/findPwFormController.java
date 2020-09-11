package com.login.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

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
import com.commonCode.VO.commonCodeVO;
import com.email.Service.MailService;
import com.login.Service.loginService;
import com.login.VO.userInfoVO;
import com.login.Validator.findPwUserInfoValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class findPwFormController {
	@Resource(name="loginService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private loginService loginService;
	
	@Resource(name="MailService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private MailService MailService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	@Inject
    PasswordEncoder passwordEncoder;	// ��ȣȭ ��� �߰�
	
	@Autowired
	findPwUserInfoValidator findPwUserInfoValidator;// validator ���� �ҷ���
	
	
	private static final Logger logger = LoggerFactory.getLogger(findPwFormController.class);
	
	/**
	 * IDã�� �˾� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findPwForm.do", method = RequestMethod.GET)
	public String findIdForm(Model model) throws Exception {
		List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("pwHint");
		
		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("pwHint", codeResult);
		model.addAttribute("userInfoVO", new userInfoVO());
		return "jsp/login/findPw";
	}
	
	/**
	 * PWã��
	 * @param userInfoVO
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/findPw.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findPw(@ModelAttribute("userInfoVO") userInfoVO userInfoVO, BindingResult bindingResult) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		/** ������ ����(����) **/
		findPwUserInfoValidator findPwUserInfoValidator = new findPwUserInfoValidator();
		findPwUserInfoValidator.validate(userInfoVO, bindingResult);
		
		
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
		
		userInfoVO resultVO = loginService.selectUserInfoWithData(userInfoVO);

		if(resultVO == null) {
			mReturn.put("result", "fail");
			mReturn.put("message", "��ġ�ϴ� ������ �����ϴ�.");
			
			return mReturn;
		}
		else {
			// �ӽú�й�ȣ ����
			String tempPw = UUID.randomUUID().toString().replaceAll("-", ""); // -�� ������ �־���.
			tempPw = tempPw.substring(0, 10);
			
			resultVO.setUserPw(tempPw);
			
			//�ӽú�й�ȣ ��ȣȭ
			resultVO.setUserPw(passwordEncoder.encode(resultVO.getUserPw()));
			loginService.updatePw(resultVO);
			
			//�߼� ���� ����
			String adminEmail = "ksm1538@gmail.com";		// �ڱ� gmail ID �Է��ϼ�
			
			String mailTitle = "YonginStudy : �ӽ� ��й�ȣ �߱� ����";
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("<div style=\"font-size:20px\">�ȳ��ϼ���.<br>");
			stringBuilder.append("YonginStudy�Դϴ�. <br>");
			stringBuilder.append("������ �ӽ� ��й�ȣ�� <strong style=\"color:red\"> " + tempPw + "</strong> �Դϴ�.<br>");
			stringBuilder.append("�α��� �� ��й�ȣ�� �������ּ���.<br></div>");
			
			//���� ���� �����ϸ� true, �����ϸ� false ��ȯ
			boolean result = MailService.send(mailTitle, stringBuilder.toString(), adminEmail, resultVO.getUserEmail(), null);
			
			if(result == true) {
				mReturn.put("result", "success");
				mReturn.put("message", "�̸��Ϸ� �ӽ� ��й�ȣ�� �����߽��ϴ�.");
				
				return mReturn;
			}else {
				mReturn.put("result", "fail");
				mReturn.put("message", "�̸��� ���ۿ� �����Ͽ����ϴ�.");
				
				return mReturn;
			}
		}
	}
}
