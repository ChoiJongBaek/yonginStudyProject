package com.studyManagement.Controller;

import java.util.HashMap;
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
import com.commonFunction.Controller.yonginFunction;
import com.login.VO.userInfoVO;
import com.studyManagement.Service.studyManagementService;

@Controller
public class studyMemberInfoDetailController {
	@Resource(name="studyManagementService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyManagementService studyManagementService;

	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	/**
	 * ���͵� ��� �󼼺��� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/studyManagement/studyMemberInfoDetail.do", method = RequestMethod.POST)
	public String studyMemberInfoDetail(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		model.addAttribute("userInfoVO", new userInfoVO());
		
		return "jsp/studyManagement/studyMemberInfoDetail";
	}
	
	/**
	 * ���͵� �� ���� 
	 * @param systemNoticeCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/selectStudyMemberInfoDetail.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyInfoDetail(@RequestBody String userCode) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(userCode == null || userCode.equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		userInfoVO userInfoVO = studyManagementService.selectStudyMemberManage(userCode);
		
		if(userInfoVO == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		String birth = yonginFunction.nullConvert(userInfoVO.getUserBirth());
		userInfoVO.setUserBirth(yonginFunction.addMinusChar(birth));
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ��ȸ�Ͽ����ϴ�.");
		mReturn.put("boardInfo", userInfoVO);
		
		return mReturn;
	}
}
