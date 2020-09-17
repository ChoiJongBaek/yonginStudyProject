package com.study.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import com.study.Validator.studyInfoValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class makeStudyController {
	@Resource(name="studyService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyService studyService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	@Autowired
	studyInfoValidator studyInfoValidator;
	
	private static final Logger logger = LoggerFactory.getLogger(makeStudyController.class);
	/**
	 * ���͵� ���� �˾� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/makeStudy.do", method = RequestMethod.GET)
	public String studyMakeForm(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		List<commonCodeVO> studyTopiccodeResult = commonCodeService.selectCommonCodeList("studyTopic");
		List<commonCodeVO> studyLimitcodeResult= commonCodeService.selectCommonCodeList("studyLimit");
		
		model.addAttribute("studyTopic", studyTopiccodeResult);
		model.addAttribute("studyLimit", studyLimitcodeResult);
		model.addAttribute("studyInfoVO", new studyInfoVO());
		return "jsp/study/makeStudy";
	}
	
	/**
	 * ���͵� ����
	 * @param studyInfoVO
	 * @param bingdingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/makeStudy.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerAjaxFunction(@ModelAttribute("studyInfoVO") studyInfoVO studyInfoVO, HttpSession session, BindingResult bindingResult) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		/** ������ ����(����) **/
		studyInfoValidator studyInfoValidator = new studyInfoValidator();
		studyInfoValidator.validate(studyInfoVO, bindingResult);
		
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMsg = "";
		    for (FieldError error : errors ) {
		    	errorMsg += error.getDefaultMessage() + "\n";
		    }
		    
		    mReturn.put("result", "fail");
			mReturn.put("message", errorMsg);
			
			return mReturn;
			
		}
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		studyInfoVO.setStudyRgstusId(user.getUserCode());
		studyInfoVO.getStudyRgstusId();
		
		studyService.insertStudy(studyInfoVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� �����Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ���͵� �̸� �ߺ�üũ
	 * @param studyName
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
 