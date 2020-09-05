package com.login.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.login.VO.userInfoVO;

@Component
public class userInfoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
        // Ÿ���� �ȸ����� invalid target for Validator ������ ���!
		return userInfoVO.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		ValidationUtils.rejectIfEmpty(error, "userId", "userInfoVO.userId.empty","���̵� �Է����ּ���.");
		ValidationUtils.rejectIfEmpty(error, "userPw", "userInfoVO.userPw.empty","��й�ȣ�� �Է����ּ���");
       
		userInfoVO userInfoVO = (userInfoVO) obj;
		
		/*
		 * //���� �̸����� ���� �����ϰ�ʹٸ� Pattern Ŭ������ �̿�����! Pattern pattern =
		 * Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
		 * Pattern.CASE_INSENSITIVE); if
		 * (!(pattern.matcher(memberDto.getId()).matches())) { err.rejectValue("id",
		 * "member.email.invalid"); }
		 */
	}
}
