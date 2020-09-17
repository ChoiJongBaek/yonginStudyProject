package com.main.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;
import com.login.VO.userInfoVO;
import com.main.Service.mainService;
import com.main.VO.studyInfoVO;
/**
 * Handles requests for the application home page.
 */
@Controller
public class mainController {
	@Resource(name="mainService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private mainService mainService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	private static final Logger logger = LoggerFactory.getLogger(mainController.class);
	
	/**
	 * �α��� ȭ�� Controller
	 * @throws Exception 
	 */
	@RequestMapping(value = "/main/main.do", method = RequestMethod.GET)
	public String login(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		
		List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("studyTopic");
		
		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("studyTopicArray", codeResult);
		
		return "jsp/main/main";
	}
	
	/**
	 * ���͵� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/main/selectStudyList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyList(HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
		List<studyInfoVO> ltResult = mainService.selectStudyList(user.getUserCode());
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���͵� ����� �����ϴ�.");
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ �����Ͽ����ϴ�.");
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
	
	/*	���� ��ȸ �Լ� ��������(�輺��)
	@RequestMapping(value="/main/selectCalenderList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectCalenderList(HttpServletRequest request) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		HttpSession session = request.getSession();
		userInfoVO userInfoVO = (userInfoVO) session.getAttribute("user");
		System.out.println("���� : "+userInfoVO.getUserId());
		
		List<calenderVO> ltResult = mainService.selectCalenderList();
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���� ����� �����ϴ�.");
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ �����Ͽ����ϴ�.");
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
	*/
}
	
	
	