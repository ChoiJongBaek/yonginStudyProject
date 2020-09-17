package com.message.Validator;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.message.VO.messageInfoVO;

@Component
public class sendMessageValidator implements Validator{

	@Inject
    private MessageSource msg;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return messageInfoVO.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		messageInfoVO messageInfoVO = (messageInfoVO) obj;
		
		/******** ��Ģ �˻�(����) ********/
		
		/** ������ ���̵� ����(����) **/
		//���� ���� �� �� üũ 
		ValidationUtils.rejectIfEmpty(error, "userCodeTo", "","�޴� ��� ���̵� �Է����ּ���.");
   	    /** ������ ���̵� ����(��) **/
		
		/** ���� ���� ����(����) **/
		//���� ���� �� �� üũ 
		ValidationUtils.rejectIfEmpty(error, "messageTitle", "","���� ������ �Է����ּ���.");
		
		//���� ���� ���� üũ
		if(messageInfoVO.getMessageTitle().length() > 30) {
			error.rejectValue("title", "", "���� ������ 30���ڱ��� �Է°����մϴ�.");
		}
   	    /** ���� ���� ����(��) **/
   	    
		/** ���� ���� ����(����) **/
		//���� ���� �� �� üũ 
		ValidationUtils.rejectIfEmpty(error, "messageDesc", "","���� ������ �Է����ּ���.");
   	    /** ���� ���� ����(��) **/

        /******** ��Ģ �˻�(��) ********/
	}
}
