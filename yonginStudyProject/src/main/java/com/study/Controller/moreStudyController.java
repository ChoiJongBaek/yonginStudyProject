package com.study.Controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;
import com.login.VO.userInfoVO;
import com.main.VO.studyInfoVO;
import com.study.Service.studyService;

@Controller
public class moreStudyController {
	@Resource(name="studyService")
	private studyService studyService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	private static final Logger logger = LoggerFactory.getLogger(moreStudyController.class);
	/**
	 * ���͵� �� ���� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/moreStudy.do", method = RequestMethod.GET)
	public String moreStudyForm(Locale locale, Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("studyTopic");
		
		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("studyTopicArray", codeResult);
		
		return "jsp/study/moreStudy";
	}
	
	/**
	 * ���͵� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/study/selectStudyList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyList(HttpServletRequest request) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		List<studyInfoVO> ltResult = studyService.selectStudyList();
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���͵� ����� �����ϴ�.");
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ �����Ͽ����ϴ�.");
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
}
