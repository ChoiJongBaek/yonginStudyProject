package com.login.Validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.commonFunction.Controller.yonginFunction;
import com.login.VO.userInfoVO;

@Component
public class findIdUserInfoValidator implements Validator{

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
		
		/** �̸� ����(����) **/
        //�̸� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "userName", "","�̸��� �Է����ּ���.");	
        /** �̸� ����(��) **/
		
		
		/** ���� ����(����) **/
        //���� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "userBirth", "","������ �Է����ּ���.");	
		
		//���� ��¥ �˻�
		if(!userInfoVO.getUserBirth().equals("")) {
			SimpleDateFormat timeFormat = new SimpleDateFormat ("yyyyMMdd");
			Date time = new Date();
			String today = timeFormat.format(time);
			
			String removeBirth = yonginFunction.remove(userInfoVO.getUserBirth(), '-');	//com.commonFunction.Controller�� �ִ� ���� �Լ��� �̿��� ���� ����
					
			if(Integer.parseInt(removeBirth) > Integer.parseInt(today)) {
				error.rejectValue("userBirth","", "������ ���� ��¥���� �۰ų� ���ƾ��մϴ�.");
			}
		}
        /** ���� ����(��) **/
		
		/** �̸��� ����(����) **/
		//�̸��� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "userEmail", "","�̸����� �Է����ּ���.");
		
		//�̸�������Ȯ��
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);
        if (!(pattern.matcher(userInfoVO.getUserEmail()).matches())) {
        	error.rejectValue("userEmail","", "�̸����� ���Ŀ� �°� �ۼ����ּ���.");
        }
		/** �̸��� ����(��) **/
        
        /******** ��Ģ �˻�(��) ********/
	}
}
