package com.studyManagement.Controller;


import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.login.VO.userInfoVO;

@Controller
public class studyNoticeController {
	
	/*
	 ���񽺴� ���� �ȸ�������Ƿ� ������ �ʿ����
	 @Resource(name="studyService") private studyService studyService;
	 */
	
	/*
	�����ڵ� �ʿ�� ���
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	*/
	
	/*��Ʈ�ѷ� �̸��̶� ����*/
	private static final Logger logger = LoggerFactory.getLogger(studyNoticeController.class);
	
	/**
	 * ���͵� ���� �������� ������
	 * @throws Exception 
	 */
	@RequestMapping(value = "/studynotice.do", method = RequestMethod.POST)
	public String studyNoticeForm(Locale locale, Model model, HttpSession session) throws Exception {
		/*�ǵ��ʿ���� ������ ���°�*/
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		/*���͵����ȿ� ���õ� �����͸�  db���� �������� �װ� coderesult�� ����*/
		/*List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("studyTopic");*/
		
		//model ������ �����͸� ��� jsp�� ����
		/*coderesult�� moodel�� �־��*/
		/*model.addAttribute("studyTopicArray", codeResult);*/
		
		return "jsp/studyManagement/studyNotice";
	}
	
	
	/**
	 * �������� �ۼ��˾�
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/studyManagement/writeStudyNotice.do", method = RequestMethod.GET)
	public String writeStudyNoticePopup(HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� ������ �� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		return "jsp/studyManagement/writeStudyNotice";
	}
	
	
	
}