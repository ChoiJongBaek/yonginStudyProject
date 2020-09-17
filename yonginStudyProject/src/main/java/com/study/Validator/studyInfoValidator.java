package com.study.Validator;

import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.main.VO.studyInfoVO;

@Component
public class studyInfoValidator implements Validator{

	@Inject
    private MessageSource msg;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return studyInfoVO.class.equals(clazz);
	}
	
	@Override
	public void validate(Object obj, Errors error) {
		studyInfoVO studyInfoVO = (studyInfoVO) obj;
		
		/******** ��Ģ �˻�(����) ********/
		
		/** ���͵� �̸� ����(����) **/
		//���͵� �̸� �� �� üũ 
		ValidationUtils.rejectIfEmpty(error, "studyName", "","���͵� �̸��� �Է����ּ���.");
		
		//���͵� �̸� ���� üũ
		if(studyInfoVO.getStudyName().length() < 5 || studyInfoVO.getStudyName().length() > 30) {
			error.rejectValue("studyName", "", "���͵� �̸��� 5~30�ڷ� �������ּ���.");
		}
		
		//���͵� �̸� ����, ����, �ѱ� Ȯ��
		boolean flag = Pattern.matches("^[a-zA-Z0-9��-�R\s]*$", studyInfoVO.getStudyName());
   	    if(flag == false) {
   	    	error.rejectValue("studyName","", "���͵� �̸��� ����, ����, �ѱ۸� �Է°����մϴ�.");
   	    }
   	    /** ���͵� �̸� ����(��) **/
   	    
   	    /** ���͵� ���� ����(����) **/
        //���� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "studyDesc", "","���͵� ������ �Է����ּ���.");	
        /** ���͵� ���� ����(��) **/
		
		/** ���͵� ���� ����(����) **/
        //���� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "studyTopic", "","���͵� ������ �Է����ּ���.");	
        /** ���͵� ���� ����(��) **/
		
		/** ���͵� �����ο� ����(����) **/
        //�����ο� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "studyLimit", "","���͵� �����ο��� �Է����ּ���.");	
        /** ���͵� �����ο� ����(��) **/
		
		/** ���͵� ���� ����(����) **/
        //���� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "studyArea", "","���͵� ������ �Է����ּ���.");	
        /** ���͵� ���� ����(��) **/
		
		/******** ��Ģ �˻�(��) ********/
	
	}
}
