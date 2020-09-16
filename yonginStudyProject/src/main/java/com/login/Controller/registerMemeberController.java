package com.login.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.inject.Inject;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;
import com.commonFunction.Controller.yonginFunction;
import com.email.Service.MailService;
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
	
	@Resource(name="MailService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private MailService MailService;
	
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
		userInfoValidator userInfoValidator = new userInfoValidator();
		userInfoValidator.validate(userInfoVO, bindingResult);
		
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
		
		//��й�ȣ ��ȣȭ
		userInfoVO.setUserPw(passwordEncoder.encode(userInfoVO.getUserPw()));
		
		// ��¥���� '-' ����
		String removeBirth = yonginFunction.remove(userInfoVO.getUserBirth(), '-');	//com.commonFunction.Controller�� �ִ� ���� �Լ��� �̿��� ���� ����
		userInfoVO.setUserBirth(removeBirth);
		
		// ��ȭ��ȣ���� '-' ����
		String removePhoneNumber = yonginFunction.remove(userInfoVO.getUserPhoneNumber(), '-');	//com.commonFunction.Controller�� �ִ� ���� �Լ��� �̿��� ���� ����
		userInfoVO.setUserPhoneNumber(removePhoneNumber);
		
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
	
	/**
	 * �̸��� ����
	 * @param userEmail
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/register/sendEmailAuthCode.json", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendEmailAuthCode(@RequestBody String userEmail, HttpSession session) throws Exception{
	
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);
        if (!(pattern.matcher(userEmail).matches())) {
        	mReturn.put("result", "fail");
			mReturn.put("message", "�̸��� ���Ŀ� �°� �Է����ּ���.");
			
			return mReturn;
        }
        
        // ������� �̸����� �ִ��� Ȯ��
        int count = loginService.selectSameEmail(userEmail);
        if(count != 0) {
        	mReturn.put("result", "fail");
			mReturn.put("message", "�ش� �̸����� �̹� ������Դϴ�.");
			
			return mReturn;
        }
        
        //���� �߻�
		int randomNumber = new Random().nextInt(900000) + 100000;
		String authCode = String.valueOf(randomNumber);
		
		//�߼� ���� ����
		String adminEmail = "ID@gmail.com";		// �ڱ� gmail ID �Է��ϼ�
		
		String mailTitle = "YonginStudy : ���� ���� �ڵ�";
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<div style=\"font-size:20px\">�ȳ��ϼ���.<br>");
		stringBuilder.append("YonginStudy�� ������ּż� �����մϴ�. <br>");
		stringBuilder.append("������ ���� �ڵ�� <strong style=\"color:red\"> " + authCode + "</strong> �Դϴ�.<br>");
		stringBuilder.append("��� �����Ͻ÷��� �����ڵ带 �Է����ּ���.<br></div>");
		
		session.setAttribute("authCode", authCode);		//���ǿ� ������ȣ ����
		
		//���� ���� �����ϸ� true, �����ϸ� false ��ȯ
		boolean result = MailService.send(mailTitle, stringBuilder.toString(), adminEmail, userEmail, null);
		
		if(result == true) {
			mReturn.put("result", "success");
			mReturn.put("message", "������ȣ�� ���۵Ǿ����ϴ�.");
			
			return mReturn;
		}
		else {
			session.removeAttribute("authCode");	//���ǿ��� �����ڵ� ����
			mReturn.put("result", "fail");
			mReturn.put("message", "������ȣ ���ۿ� �����߽��ϴ�.");
			
			return mReturn;
		}
	}

	/**
	 * �̸��� ���� �ڵ� Ȯ��
	 * @param userAuthCode
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/register/checkEmailAuthCode.json", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkEmailAuthCode(@RequestBody String userAuthCode, HttpSession session){
	
		HashMap<String, Object> mReturn = new HashMap<String, Object>();

		String authCode = (String) session.getAttribute("authCode");	//���ǿ��� ������ȣ �ҷ���
		
		// ������ȣ�� ���� ���
		if(authCode.equals(userAuthCode)) {
			mReturn.put("result", "success");
			mReturn.put("message", "�̸��� ������ �����Ͽ����ϴ�.");
			
			return mReturn;
		}else {
			mReturn.put("result", "fail");
			mReturn.put("message", "�̸��� ������ �����Ͽ����ϴ�.");
			
			return mReturn;
		}
	}
	
	/**
	 * email �ʱ�ȭ
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/register/resetEmail.json", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetEmail(HttpSession session){
	
		HashMap<String, Object> mReturn = new HashMap<String, Object>();

		session.removeAttribute("authCode");	//���ǿ��� �����ڵ� ����

		mReturn.put("result", "success");
		mReturn.put("message", "�̸����� �Է����ּ���.");
		
		return mReturn;
	}
}
