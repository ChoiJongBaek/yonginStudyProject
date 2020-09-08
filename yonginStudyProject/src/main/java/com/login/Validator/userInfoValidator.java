package com.login.Validator;

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
		ValidationUtils.rejectIfEmpty(error, "userId", "userInfoVO.userId.empty","���̵� �Է����ּ���.");
		ValidationUtils.rejectIfEmpty(error, "userPw", "userInfoVO.userPw.empty","��й�ȣ�� �Է����ּ���");
       
		
		/**
		 * ����,���� ����X
		 * 
		 */
		/*userInfoVO userInfoVO = (userInfoVO) obj;
		if(userInfoVO.getUserId().length() < 5 || userInfoVO.getUserId().length() > 20) {
			error.rejectValue("userId", "�޼����ڵ��Է�");
		}*/
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
