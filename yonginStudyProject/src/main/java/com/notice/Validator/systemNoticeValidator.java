package com.notice.Validator; 

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.notice.VO.moreNoticeInfoVO;

@Component
public class systemNoticeValidator implements Validator{

	@Inject
    private MessageSource msg;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return moreNoticeInfoVO.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		moreNoticeInfoVO moreNoticeInfoVO = (moreNoticeInfoVO) obj;
		
		/******** ��Ģ �˻�(����) ********/
		
		/** �������� ���� ����(����) **/
		ValidationUtils.rejectIfEmpty(error, "systemNoticeTitle", "","�������� ������ �Է����ּ���.");
   	    /** �������� ���� ����(��) **/
		
		/** �������� ���� ����(����) **/
		ValidationUtils.rejectIfEmpty(error, "systemNoticeDesc", "","�������� ������ �Է����ּ���.");
   	    /** �������� ���� ����(��) **/

        /******** ��Ģ �˻�(��) ********/
	}
}
