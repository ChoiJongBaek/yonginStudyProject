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
public class userInfoValidator implements Validator{

	@Inject
    private MessageSource msg;
	
	@Override
	public boolean supports(Class<?> clazz) {
        // Ÿ���� �ȸ����� invalid target for Validator ������ ���!
		return userInfoVO.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		ValidationUtils.rejectIfEmpty(error, "userId", "","���̵� �Է����ּ���.");
		ValidationUtils.rejectIfEmpty(error, "userPw", "","��й�ȣ�� �Է����ּ���");
		ValidationUtils.rejectIfEmpty(error, "userPwHintAnswer", "","��й�ȣ ��Ʈ ���� �Է����ּ���");
		ValidationUtils.rejectIfEmpty(error, "userName", "","�̸��� �Է����ּ���");
		
		/**
		 * ����,���� ����X
		 * 
		 */
		userInfoVO userInfoVO = (userInfoVO) obj;
		
		
		
		//ID ���� üũ
		if(userInfoVO.getUserId().length() < 5 || userInfoVO.getUserId().length() > 20) {
			error.rejectValue("userId", "", "���̵� 5~20�ڷ� �������ּ���");
		}
		
		//��й�ȣ ���� üũ
		if(userInfoVO.getUserPw().length() < 8 || userInfoVO.getUserPw().length() > 20) {
			error.rejectValue("userPw", "", "��й�ȣ�� 8~20�ڷ� �������ּ���.");
		}
		
		//��й�ȣ ��й�ȣȮ�� üũ
		if(!(userInfoVO.getUserPw().equals(userInfoVO.getUserPwConfirm()))){
			error.rejectValue("userPw", "", "��й�ȣ�� ��й�ȣ Ȯ���� �ٸ��ϴ�.");
		}
		
		//ID ������� Ȯ��
		boolean flag = Pattern.matches("^[a-zA-Z0-9]*$", userInfoVO.getUserId());
   	    if(flag == false) {
   	    	  error.rejectValue("userId","", "ID�� ����, ���ڸ� �Է°����մϴ�.");
   	    }
		//��й�ȣ �������Ư��Ȯ��
		
		//�̸�������Ȯ��
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);
          if (!(pattern.matcher(userInfoVO.getUserEmail()).matches())) {
        	  error.rejectValue("userEmail","", "�̸����� ���Ŀ� �°� �ۼ����ּ���.");
          }
          
		//����Ȯ��
		
		//
		//
		
		/*
		 * //���� �̸����� ���� �����ϰ�ʹٸ� Pattern Ŭ������ �̿�����! Pattern pattern =
		 * Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
		 * Pattern.CASE_INSENSITIVE); if
		 * (!(pattern.matcher(memberDto.getId()).matches())) { err.rejectValue("id",
		 * "member.email.invalid"); }
		 */
	}
}
