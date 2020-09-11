package com.login.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.email.Service.MailService;
import com.login.Service.loginService;
import com.login.VO.userInfoVO;
import com.login.Validator.findIdUserInfoValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class findIdFormController {
	@Resource(name="loginService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private loginService loginService;
	
	@Resource(name="MailService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private MailService MailService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	@Autowired
	findIdUserInfoValidator findIdUserInfoValidator;// validator ���� �ҷ���
	
	
	private static final Logger logger = LoggerFactory.getLogger(findIdFormController.class);
	
	/**
	 * IDã�� �˾� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findIdForm.do", method = RequestMethod.GET)
	public String findIdForm(Model model) throws Exception {
		
		model.addAttribute("userInfoVO", new userInfoVO());
		return "jsp/login/findId";
	}
	
	/**
	 * IDã��
	 * @param userInfoVO
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/findId.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findId(@ModelAttribute("userInfoVO") userInfoVO userInfoVO, BindingResult bindingResult) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		/** ������ ����(����) **/
		findIdUserInfoValidator findIdUserInfoValidator = new findIdUserInfoValidator();
		findIdUserInfoValidator.validate(userInfoVO, bindingResult);
		
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
		
		// ���Ͽ��� '-' ����
		String removeBirth = yonginFunction.remove(userInfoVO.getUserBirth(), '-');	//com.commonFunction.Controller�� �ִ� ���� �Լ��� �̿��� ���� ����
		userInfoVO.setUserBirth(removeBirth);
		
		String Id = loginService.selectIdWithData(userInfoVO);
		
		if(Id != null && !Id.equals("")) {
			mReturn.put("result", "success");
			mReturn.put("message", "���������� ã�ҽ��ϴ�.");
			mReturn.put("resultData", Id);
			
			return mReturn;
		}else {
			mReturn.put("result", "fail");
			mReturn.put("message", "��ġ�ϴ� ������ �����ϴ�.");
			
			return mReturn;
		}
	}
	
}
