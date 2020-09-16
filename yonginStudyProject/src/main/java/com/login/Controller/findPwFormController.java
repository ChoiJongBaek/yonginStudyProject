package com.login.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.login.Validator.findPwUserInfoValidator;
import com.login.Validator.pwChangeValidator;

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
	
	@Autowired
	pwChangeValidator pwChangeValidator;// validator ���� �ҷ���
	
	private static final Logger logger = LoggerFactory.getLogger(findPwFormController.class);
	
	/**
	 * Pwã�� �˾� Mapping
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
	 * PWã�� ���̵� �̸��� ���� Ȯ��
	 * @param userInfoVO
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/findPw.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findPw(@ModelAttribute("userInfoVO") userInfoVO userInfoVO, BindingResult bindingResult, HttpSession session) throws Exception {
	      
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
		
		// ��¥���� '-' ����
		String removeBirth = yonginFunction.remove(userInfoVO.getUserBirth(), '-');	//com.commonFunction.Controller�� �ִ� ���� �Լ��� �̿��� ���� ����
		userInfoVO.setUserBirth(removeBirth);
		
		userInfoVO resultVO = loginService.selectUserInfoWithData(userInfoVO);

		if(resultVO == null) {
			mReturn.put("result", "fail");
			mReturn.put("message", "��ġ�ϴ� ������ �����ϴ�.");
			
			return mReturn;
		}
		else {
			// ��ġ�ϴ� ���� �ִ� ��� ���ǿ� ����
			session.setAttribute("findPw", resultVO);
			
			mReturn.put("result", "success");
			mReturn.put("message", "������ ��ġ�ϴ� ���̵� ã�ҽ��ϴ�.");
			mReturn.put("url", "/findPwSelectForm.do");
			
			return mReturn;
		}
	}
	
	/**
	 * PWã�� ��� ���� �˾�
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findPwSelectForm.do")
	public String findPwSelectForm(Model model, HttpSession session) throws Exception {
		
		userInfoVO user = (userInfoVO) session.getAttribute("findPw");
		
		if(user == null) {
			return "jsp/login/login";
		}
		
		model.addAttribute("findPwEmail", user.getUserEmail());
		
		return "jsp/login/findPwSelect";
	}
	
	/**
	 * �̸����� �̿��� �ӽú�й�ȣ �߱޹ޱ�
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/findPwUsingEmail.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findPwUsingEmail(HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();

		// ��й�ȣ ã�⿡�� ���ǿ� ���� ����� ���� ������
		userInfoVO user = (userInfoVO) session.getAttribute("findPw");

		if(user == null) {
			mReturn.put("result", "fail");
			mReturn.put("message", "�߸��� �����Դϴ�.");
			
			return mReturn;
		}
		
		// �ӽú�й�ȣ ����
		String tempPw = UUID.randomUUID().toString().replaceAll("-", ""); // -�� ������ �־���.
		tempPw = tempPw.substring(0, 10);
		
		user.setUserPw(tempPw);
		
		// �ӽú�й�ȣ ��ȣȭ
		user.setUserPw(passwordEncoder.encode(user.getUserPw()));
		
		// ��й�ȣ ������Ʈ
		loginService.updatePw(user);
		
		//�߼� ���� ����
		String adminEmail = "ksm1538@gmail.com";		// �ڽ� gmail ID
		
		String mailTitle = "YonginStudy : �ӽ� ��й�ȣ �߱� ����";
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<div style=\"font-size:20px\">�ȳ��ϼ���.<br>");
		stringBuilder.append("YonginStudy�Դϴ�. <br>");
		stringBuilder.append("������ �ӽ� ��й�ȣ�� <strong style=\"color:red\"> " + tempPw + "</strong> �Դϴ�.<br>");
		stringBuilder.append("�α��� �� ��й�ȣ�� �������ּ���.<br></div>");
		
		//���� ���� �����ϸ� true, �����ϸ� false ��ȯ
		boolean result = MailService.send(mailTitle, stringBuilder.toString(), adminEmail, user.getUserEmail(), null);
		
		if(result == true) {
			mReturn.put("result", "success");
			mReturn.put("message", "�̸��Ϸ� �ӽ� ��й�ȣ�� �����߽��ϴ�.");
			
			session.invalidate();	// ���� ����
			
			return mReturn;
		}else {
			mReturn.put("result", "fail");
			mReturn.put("message", "�̸��� ���ۿ� �����Ͽ����ϴ�.");
			
			return mReturn;
		}
	}
	
	/**
	 * PWã�� �ӽú�й�ȣ �̿��� �˾� 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findPwUsingHintForm.do")
	public String findPwUsingHintForm(Model model, HttpSession session) throws Exception {
		List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("pwHint");
		
		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("pwHint", codeResult);
		
		return "jsp/login/findPwUsingHint";
	}
	
	/**
	 * ��й�ȣ ��Ʈ Ȯ��
	 * @param userInfoVO
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/findPwUsingHint.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findPwUsingHint(@RequestBody userInfoVO userInfoVO, HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();

		if(userInfoVO.getUserPwHintCode().equals("") || userInfoVO.getUserPwHintAnswer().equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "��й�ȣ ��Ʈ�� ���� �Է����ּ���.");
			
			return mReturn;
		}
		// ��й�ȣ ã�⿡�� ���ǿ� ���� ����� ���� ������
		userInfoVO user = (userInfoVO) session.getAttribute("findPw");
		
		if(user == null) {
			mReturn.put("result", "fail");
			mReturn.put("message", "�߸��� �����Դϴ�.");
			
			return mReturn;
		}
		
		userInfoVO.setUserCode(user.getUserCode());

		//��й�ȣ ��Ʈ�� ���� ��ġ�ϸ� 1 ��ȯ
		int result = loginService.selectUserPwHint(userInfoVO);
		
		
		if(result == 1) {
			mReturn.put("result", "success");
			mReturn.put("message", "��й�ȣ ��Ʈ�� ���� ��ġ�մϴ�.\n��й�ȣ�� �������ֽʽÿ�.");
			mReturn.put("url", "/findPwChangePw.do");
			
			return mReturn;
		}else {
			mReturn.put("result", "fail");
			mReturn.put("message", "��й�ȣ ��Ʈ�� ���� ��ġ���� �ʽ��ϴ�.");
			
			return mReturn;
		}
	}
	
	/**
	 * ��й�ȣ ���� ������
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findPwChangePw.do")
	public String findPwChangePw(Model model) throws Exception {
		
		model.addAttribute("userInfoVO", new userInfoVO());
		return "jsp/login/findPwChangePw";
	}
	
	/**
	 * ��й�ȣ ����
	 * @param userInfoVO
	 * @param session
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/findPwChangePw.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findPwChangePw(@ModelAttribute("userInfoVO") userInfoVO userInfoVO, HttpSession session, BindingResult bindingResult) throws Exception {
	      
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
		userInfoVO user = (userInfoVO) session.getAttribute("findPw");
		
		if(user == null) {
			mReturn.put("result", "fail");
			mReturn.put("message", "�߸��� �����Դϴ�.");
			
			return mReturn;
		}
		
		userInfoVO.setUserCode(user.getUserCode());

		// ��й�ȣ ��ȣȭ
		userInfoVO.setUserPw(passwordEncoder.encode(userInfoVO.getUserPw()));
				
		// ��й�ȣ ������Ʈ
		loginService.updatePw(userInfoVO);
		
		session.invalidate();
		
		mReturn.put("result", "success");
		mReturn.put("message", "��й�ȣ�� ����Ǿ����ϴ�.");
		
		return mReturn;
		
	}
}
