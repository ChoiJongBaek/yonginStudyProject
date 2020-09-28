package com.qna.Controller;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;
import com.login.VO.userInfoVO;
import com.main.VO.studyInfoVO;
import com.study.Service.studyService;

@Controller
public class makeQnaController {
	
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
	private static final Logger logger = LoggerFactory.getLogger(makeQnaController.class);
	/**
	 * ���͵� �� ���� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/makeQna.do", method = RequestMethod.GET)
	public String makeQnaForm(Locale locale, Model model, HttpSession session) throws Exception {
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
		
		return "jsp/qna/makeQna";
	}
}