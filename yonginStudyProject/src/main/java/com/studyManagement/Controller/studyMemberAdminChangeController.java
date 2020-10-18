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

@Controller
public class studyMemberAdminChangeController {
	@Resource(name="studyManagementService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyManagementService studyManagementService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	/**
	 * ���͵� ��� ���� ���� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/studyManagement/studyMemberAdminChange.do", method = RequestMethod.POST)
	public String studyMemberAdminChangeForm(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
	
		List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("studyAuthority");
		
		model.addAttribute("userInStudyVO", new userInStudyVO());
		model.addAttribute("studyAuthorityArray", codeResult);
		
		
		return "jsp/studyManagement/studyMemberAdminChange";
	}
	
	/**
	 * ��� ���� Ȯ��â ���� 
	 * @param studyCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/selectStudyMemberAdminView.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyMemberAdminView(@RequestBody userInStudyVO userInStudyVO) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(userInStudyVO.getUserCode() == null || userInStudyVO.getUserCode().equals("") || userInStudyVO.getStudyCode() == null || userInStudyVO.getStudyCode().equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}

		userInStudyVO data = studyManagementService.selectStudyMemberAdminView(userInStudyVO);
		if(data == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ��ȸ�Ͽ����ϴ�.");
		mReturn.put("boardInfo", data);
		
		return mReturn;
	}
	
	/** 
	 * ���͵� ��� ���� ����
	 * @param studyInfoVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/studyMemberAdminChange.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> studyInfoChange(@RequestBody userInStudyVO userInStudyVO, HttpSession session) throws Exception {
 
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
	
		// ������ ���� �Ұ� ������ ���� 
		
		studyManagementService.studyMemberAdminChange(userInStudyVO);
		
		
		mReturn.put("result", "success");
		mReturn.put("message", "������ ����Ǿ����ϴ�.");
		
		return mReturn;
	}

}
