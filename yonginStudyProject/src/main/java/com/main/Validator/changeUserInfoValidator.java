package com.main.Validator;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.login.VO.userInfoVO;

@Component
public class changeUserInfoValidator implements Validator{

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
		
		/** �̸��� ����(����) **/
		//�̸��� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "userEmail", "","�̸����� �Է����ּ���.");
		
		//�̸�������Ȯ��
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);
        if (!(pattern.matcher(userInfoVO.getUserEmail()).matches())) {
        	error.rejectValue("userEmail","", "�̸����� ���Ŀ� �°� �ۼ����ּ���.");
        }
		/** �̸��� ����(��) **/
		
        
        /** ��ȭ��ȣ ����(����) **/
        //��ȭ��ȣ �� �� üũ
        ValidationUtils.rejectIfEmpty(error, "userPhoneNumber", "","�޴��� ��ȣ�� �Է����ּ���.");
        //��ȭ��ȣ ���� üũ
        boolean flag3 = Pattern.matches("^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$", userInfoVO.getUserPhoneNumber());
   	    if(flag3 == false) {
   	    	error.rejectValue("userPhoneNumber","", "�޴��� ��ȣ�� ���Ŀ� �°� �Է����ּ���.");
   	    }
        /** ��ȭ��ȣ ����(��) **/
        
        
        /** �ּ� ����(����) **/
        //�ּ� �� �� üũ
        ValidationUtils.rejectIfEmpty(error, "userAddress", "","�ּҸ� �Է����ּ���.");
        /** �ּ� ����(��) **/
        
        
        /******** ��Ģ �˻�(��) ********/
	}
}
