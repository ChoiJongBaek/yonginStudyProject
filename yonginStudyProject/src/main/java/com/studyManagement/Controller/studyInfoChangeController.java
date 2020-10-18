package com.studyManagement.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
import com.main.VO.userInStudyVO;
import com.studyManagement.Service.studyManagementService;
import com.studyManagement.Service.studyMainService;

@Controller
public class studyInfoChangeController {
	@Resource(name="studyManagementService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyManagementService studyManagementService;
	
	@Resource(name="studyMainService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyMainService studyMainService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;

	/**
	 * ���͵� ���� ���� Mapping
	 */
	@RequestMapping(value = "/studyManagemet/studyInfoChange.do", method = RequestMethod.POST)
	public String studyInfoChange(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("studyTopic");
		
		model.addAttribute("studyInfoVO", new studyInfoVO());
		model.addAttribute("studyTopicArray", codeResult);
		
		return "jsp/studyManagement/studyInfoChange"; 
	}
	
	/**
	 * ���͵� ����â ���� 
	 * @param studyCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/selectStudyInfoView.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyInfoView(@RequestBody String studyCode) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(studyCode == null || studyCode.equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		studyInfoVO studyInfoVO = studyManagementService.selectStudyInfoView(studyCode);
		
		if(studyInfoVO == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ��ȸ�Ͽ����ϴ�.");
		mReturn.put("boardInfo", studyInfoVO);
		
		return mReturn;
	}
	
	/** 
	 * ���͵� ���� ����
	 * @param studyInfoVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/studyInfoChange.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> studyInfoChange(@RequestBody studyInfoVO studyInfoVO, HttpSession session) throws Exception {
 
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
	
		// ������ ���� �Ұ� ������ ���� 
		
		/** ���� üũ(����) **/
	      userInStudyVO userinfo = new userInStudyVO();
	      
	      userinfo.setUserCode(user.getUserCode());
	      userinfo.setStudyCode(studyInfoVO.getStudyCode());
	      
	      if(userinfo.getUserCode().equals("") || userinfo.getStudyCode().equals("")) {
	         mReturn.put("result", "fail");
	         mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
	         
	         return mReturn;
	      }
	      
	      String result = studyMainService.selectStudyUserInfo(userinfo);
	      if(!result.equals("10")) {
	         mReturn.put("result", "fail");
	         mReturn.put("message", "���͵��常 ���� �����մϴ�.");
	         
	         return mReturn;
	      }
	      /** ���� üũ(��) **/
		
		studyManagementService.studyInfoChange(studyInfoVO);
		
		
		mReturn.put("result", "success");
		mReturn.put("message", "����Ǿ����ϴ�.");
		
		return mReturn;
	}
}
