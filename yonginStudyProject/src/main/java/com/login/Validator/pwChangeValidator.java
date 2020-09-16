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
public class pwChangeValidator implements Validator{

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
		
   	    /** PW ����(����) **/
   	    //��й�ȣ �� �� üũ 
   	    ValidationUtils.rejectIfEmpty(error, "userPw", "","��й�ȣ�� �Է����ּ���.");
   	    //��й�ȣ Ȯ�� �� �� üũ
   	    ValidationUtils.rejectIfEmpty(error, "userPwConfirm", "","��й�ȣ Ȯ���� �Է����ּ���.");
   	    
   	    //��й�ȣ ���� üũ
		if(userInfoVO.getUserPw().length() < 10 || userInfoVO.getUserPw().length() > 20) {
			error.rejectValue("userPw", "", "��й�ȣ�� 10~20�ڷ� �������ּ���.");
		}
   			
		//��й�ȣ, ��й�ȣȮ�� ��ġ ���� üũ
		if(!(userInfoVO.getUserPw().equals(userInfoVO.getUserPwConfirm()))){
			error.rejectValue("userPw", "", "��й�ȣ�� ��й�ȣ Ȯ���� �ٸ��ϴ�.");
		}
		
		//��й�ȣ ����,���� Ȯ��
		boolean flag2 = Pattern.matches("^[a-zA-Z0-9]*$", userInfoVO.getUserPw());
   	    if(flag2 == false) {
   	    	error.rejectValue("userPw","", "��й�ȣ�� ����, ���ڸ� �Է°����մϴ�.");
   	    }
   	    /** PW ����(��) **/
		
        /******** ��Ģ �˻�(��) ********/
	}
}
