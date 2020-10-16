package com.studyManagement.Validator;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.main.VO.calendarVO;

@Component
public class calendarValidator implements Validator{

	@Inject
    private MessageSource msg;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return calendarVO.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		calendarVO calendarVO = (calendarVO) obj;
		
		/******** ��Ģ �˻�(����) ********/
		
		//���� ���� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "title", "","���� ������ �Է����ּ���.");
		
		//���� ���� ���� üũ
		if(calendarVO.getTitle().length() > 30) {
			error.rejectValue("title", "", "���� ������ 30�ڱ��� ���� �����մϴ�.");
		}
		
		//���� ���� ���� üũ
		if(calendarVO.getContent().length() > 100) {
			error.rejectValue("content", "", "���� ������ 100�ڱ��� ���� �����մϴ�.");
		}
		
		ValidationUtils.rejectIfEmpty(error, "startDt", "","���� ��¥�� �Է����ּ���.");
		
		ValidationUtils.rejectIfEmpty(error, "startHm", "","���� �ð��� �Է����ּ���.");
		
		ValidationUtils.rejectIfEmpty(error, "endDt", "","���� ��¥�� �Է����ּ���.");
		
		ValidationUtils.rejectIfEmpty(error, "endHm", "","���� �ð��� �Է����ּ���.");
		
        
        
        /******** ��Ģ �˻�(��) ********/
	}
}
