package com.notice.Validator; 

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.notice.VO.boardVO;

@Component
public class systemNoticeValidator implements Validator{

	@Inject
    private MessageSource msg;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return boardVO.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		boardVO boardVO = (boardVO) obj;
		
		/******** ��Ģ �˻�(����) ********/
		
		/** �������� ���� ����(����) **/
		ValidationUtils.rejectIfEmpty(error, "boardTitle", "","�������� ������ �Է����ּ���.");
   	    /** �������� ���� ����(��) **/
		
		/** �������� ���� ����(����) **/
		ValidationUtils.rejectIfEmpty(error, "boardDesc", "","�������� ������ �Է����ּ���.");
   	    /** �������� ���� ����(��) **/

        /******** ��Ģ �˻�(��) ********/
	}
}
