package com.study.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.study.Service.studyService;
import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;

import com.login.VO.userInfoVO;
import com.main.VO.studyInfoVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class makeStudyController {
	@Resource(name="studyService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyService studyService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	@Inject
	
	private static final Logger logger = LoggerFactory.getLogger(makeStudyController.class);
	/**
	 * ���͵� ���� �˾� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/makeStudy.do", method = RequestMethod.GET)
	public String registerForm(Model model) throws Exception {
		List<commonCodeVO> studyTypecodeResult = commonCodeService.selectCommonCodeList("studyType");
		List<commonCodeVO> studyAreacodeResult = commonCodeService.selectCommonCodeList("studyArea");
		List<commonCodeVO> studyLimitcodeResult= commonCodeService.selectCommonCodeList("studyLimit");
		
		model.addAttribute("studyType", studyTypecodeResult);
		model.addAttribute("studyArea", studyAreacodeResult);
		model.addAttribute("studyLimit", studyLimitcodeResult);
		return "jsp/study/makeStudy";
	}
	
	/**
	 * ���͵� ����
	 * @param studyInfoVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/makeStudy.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerAjaxFunction(@RequestBody studyInfoVO studyInfoVO) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		
		//���������� �߰�
		studyService.insertStudy(studyInfoVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� �����Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ���͵� �̸� �ߺ�üũ
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/make/checkExsitingStudyName.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkExsitingStudyName(@RequestBody String studyName) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		// ��, �� ���� ����
		studyName = studyName.trim();
		
		// ���� ID�� ����ϴ� �������� ���� ��ȯ
		int count = studyService.selectSameStudyName(studyName);
		
		if(count == 0) {
			mReturn.put("result", "success");
			mReturn.put("message", "�� ���͵� �̸��� ����Ͻ� �� �ֽ��ϴ�.");
			
			return mReturn;
		}
		else {
			mReturn.put("result", "fail");
			mReturn.put("message", "�� ���͵� �̸��� �̹� ������Դϴ�.");
			
			return mReturn;
		}
	}
}
 