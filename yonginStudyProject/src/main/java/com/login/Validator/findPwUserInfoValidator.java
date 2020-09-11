package com.login.Validator;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.login.VO.userInfoVO;

@Component
public class findPwUserInfoValidator implements Validator{

	@Inject
    private MessageSource msg;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return userInfoVO.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		userInfoVO userInfoVO = (userInfoVO) obj;
		
		/******** ��Ģ �˻�(����) ********/
		
		/** �̸� ����(����) **/
        //�̸� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "userName", "","�̸��� �Է����ּ���.");	
        /** �̸� ����(��) **/

		/** ID ����(����) **/
		//ID �� �� üũ 
		ValidationUtils.rejectIfEmpty(error, "userId", "","���̵� �Է����ּ���.");
		
		//ID ���� üũ
		if(userInfoVO.getUserId().length() < 5 || userInfoVO.getUserId().length() > 20) {
			error.rejectValue("userId", "", "���̵� 5~20�ڷ� �ۼ����ּ���.");
		}
		
		//ID ����, ���� Ȯ��
		boolean flag = Pattern.matches("^[a-zA-Z0-9]*$", userInfoVO.getUserId());
   	    if(flag == false) {
   	    	error.rejectValue("userId","", "ID�� ����, ���ڸ� �Է°����մϴ�.");
   	    }
   	    /** ID ����(��) **/
   	    
   	    /** ��й�ȣ ��Ʈ �� ����(����) **/
        //��й�ȣ ��Ʈ �� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "userPwHintAnswer", "","��й�ȣ ��Ʈ ���� �Է����ּ���.");
        /** ��й�ȣ ��Ʈ �� ����(��) **/
        
        /******** ��Ģ �˻�(��) ********/
	}
}
