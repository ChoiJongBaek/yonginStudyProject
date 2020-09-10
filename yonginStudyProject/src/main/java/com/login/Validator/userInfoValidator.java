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
public class userInfoValidator implements Validator{

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
		
		/** ID ����(����) **/
		//ID �� �� üũ 
		ValidationUtils.rejectIfEmpty(error, "userId", "","���̵� �Է����ּ���.");
		
		//ID ���� üũ
		if(userInfoVO.getUserId().length() < 5 || userInfoVO.getUserId().length() > 20) {
			error.rejectValue("userId", "", "���̵� 5~20�ڷ� �������ּ���.");
		}
		
		//ID ����, ���� Ȯ��
		boolean flag = Pattern.matches("^[a-zA-Z0-9]*$", userInfoVO.getUserId());
   	    if(flag == false) {
   	    	error.rejectValue("userId","", "ID�� ����, ���ڸ� �Է°����մϴ�.");
   	    }
   	    /** ID ����(��) **/
   	    
   	    
   	    /** PW ����(����) **/
   	    //��й�ȣ �� �� üũ 
   	    ValidationUtils.rejectIfEmpty(error, "userPw", "","��й�ȣ�� �Է����ּ���.");
   	    //��й�ȣ Ȯ�� �� �� üũ
   	    ValidationUtils.rejectIfEmpty(error, "userPwConfirm", "","��й�ȣ Ȯ���� �Է����ּ���.");
   	    
   	    //��й�ȣ ���� üũ
		if(userInfoVO.getUserPw().length() < 10 || userInfoVO.getUserPw().length() > 20) {
			error.rejectValue("userPw", "", "��й�ȣ�� 10~20�ڷ� �������ּ���.");
		}
   			
		//��й�ȣ, ��й�ȣȮ�� ��ġ ���� üũ
		if(!(userInfoVO.getUserPw().equals(userInfoVO.getUserPwConfirm()))){
			error.rejectValue("userPw", "", "��й�ȣ�� ��й�ȣ Ȯ���� �ٸ��ϴ�.");
		}
		
		//��й�ȣ ����,���� Ȯ��
		boolean flag2 = Pattern.matches("^[a-zA-Z0-9]*$", userInfoVO.getUserPw());
   	    if(flag2 == false) {
   	    	error.rejectValue("userPw","", "��й�ȣ�� ����, ���ڸ� �Է°����մϴ�.");
   	    }
   	    /** PW ����(��) **/
		
		
		/** ��й�ȣ ��Ʈ �� ����(����) **/
        //��й�ȣ ��Ʈ �� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "userPwHintAnswer", "","��й�ȣ ��Ʈ ���� �Է����ּ���.");
        /** ��й�ȣ ��Ʈ �� ����(��) **/
		
		
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
		
		
		/** ���� ����(����) **/
        //���� �� �� üũ
		ValidationUtils.rejectIfEmpty(error, "userBirth", "","������ �Է����ּ���.");	
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
		
        
        /** ��ȭ��ȣ ����(����) **/
        //��ȭ��ȣ �� �� üũ
        ValidationUtils.rejectIfEmpty(error, "userPhoneNumber", "","�޴��� ��ȣ�� �Է����ּ���.");
        //��ȭ��ȣ ���� üũ
        boolean flag3 = Pattern.matches("^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$", userInfoVO.getUserPhoneNumber());
   	    if(flag3 == false) {
   	    	error.rejectValue("userPhoneNumber","", "�޴��� ��ȣ�� ���Ŀ� �°� �Է����ּ���.");
   	    }
        /** ��ȭ��ȣ ����(��) **/
        
        
        /** �ּ� ����(����) **/
        //�ּ� �� �� üũ
        ValidationUtils.rejectIfEmpty(error, "userAddress", "","�ּҸ� �Է����ּ���.");
        /** �ּ� ����(��) **/
        
        
        /******** ��Ģ �˻�(��) ********/
	}
}
