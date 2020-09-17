package com.study.Validator;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.study.VO.studyApplicationFormUserVO;

@Component
public class studyApplicationFormUserValidator implements Validator{

	@Inject
    private MessageSource msg;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return studyApplicationFormUserVO.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		studyApplicationFormUserVO studyApplicationFormUserVO = (studyApplicationFormUserVO) obj;
		
		/******** ��Ģ �˻�(����) ********/
		
		/** ��û�� ���͵� �ڵ� ����(����) **/
		//��û�� ���� �� �� üũ 
		ValidationUtils.rejectIfEmpty(error, "studyCode", "","��û�� ���͵��� ID�� �����ϴ�.");
		
   	    /** ��û�� ���͵� �ڵ� ����(��) **/
		
		/** ��û�� ���� ����(����) **/
		//��û�� ���� �� �� üũ 
		ValidationUtils.rejectIfEmpty(error, "title", "","��û�� ������ �Է����ּ���.");
		
		//��û�� ���� ���� üũ
		if(studyApplicationFormUserVO.getTitle().length() > 30) {
			error.rejectValue("title", "", "��û�� ������ 30���ڱ��� �Է°����մϴ�.");
		}
   	    /** ��û�� ���� ����(��) **/
   	    
		/** ��û�� ���� ����(����) **/
		//��û�� ���� �� �� üũ 
		ValidationUtils.rejectIfEmpty(error, "applicationFormDesc", "","��û�� ������ �Է����ּ���.");
		
   	    /** ��û�� ���� ����(��) **/

        /******** ��Ģ �˻�(��) ********/
	}
}
