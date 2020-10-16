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
import com.main.VO.userInStudyVO;
import com.studyManagement.Service.studyMainService;
import com.studyManagement.Service.studyManagementService;

@Controller
public class studyMainController {
	@Resource(name="studyManagementService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyManagementService studyManagementService;

	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	@Resource(name="studyMainService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyMainService studyMainService;
	/**
	 * ���͵� ���� ������ Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/studyManagement/studyMain.do", method = RequestMethod.POST)
	public String studyMainForm(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		
		List<commonCodeVO> codeResult1 = commonCodeService.selectCommonCodeList("SAFStatus");
		List<commonCodeVO> codeResult2 = commonCodeService.selectCommonCodeList("studyTopic");
		List<commonCodeVO> codeResult3 = commonCodeService.selectCommonCodeList("studyAuthority");
		List<commonCodeVO> codeResult4 = commonCodeService.selectCommonCodeList("calendarType");

		model.addAttribute("applicationFormStatusArray", codeResult1);
		model.addAttribute("studyTopicArray", codeResult2);
		model.addAttribute("studyAuthorityArray", codeResult3);
		model.addAttribute("calendarType", codeResult4);
		
		return "jsp/studyManagement/studyMain";
	}
	
	/**
	 * �������� ���͵� ���� ��������
	 * @param studyCode
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/selectStudyUserInfo.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyUserInfo(@RequestBody String studyCode, HttpSession session) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		userInStudyVO userinfo = new userInStudyVO();
		
		userinfo.setUserCode(user.getUserCode());
		userinfo.setStudyCode(studyCode);
		
		if(userinfo.getUserCode().equals("") || userinfo.getStudyCode().equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		String result = studyMainService.selectStudyUserInfo(userinfo);
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ �����Ͽ����ϴ�.");
		mReturn.put("userinfo", result);
		
		return mReturn;
	}
	
	
	/**
	 * ���͵� ��� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagemet/selectStudyMainMemberList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyMemberList(@RequestBody userInStudyVO userInStudyVO) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		
		/*** ����¡(����) ***/
		int dataPerPage = 10; //�׸��� �� �������� ǥ���� ������ ��
    	int page = Integer.parseInt(userInStudyVO.getPage()); //�������� ����
    	
    	int first = page * dataPerPage + 1; //������� �߰�
    	int last = first + dataPerPage - 1; //������� �߰�
    	
    	userInStudyVO.setFirst(first); //������� �߰�
    	userInStudyVO.setLast(last);   //������� �߰�
    	
    	int total = studyManagementService.selectStudyMemberListToCnt(userInStudyVO); // �� �� ���������� Ȯ��
    	int totalPages = (int)Math.ceil(total / (double)dataPerPage); // ������� �߰�
		
		/*** ����¡(��) ***/

    	List<userInStudyVO> ltResult = studyManagementService.selectStudyMemberList(userInStudyVO);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "success");
			mReturn.put("message", "���͵�� ����� �����ϴ�.");
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ �����Ͽ����ϴ�.");
		mReturn.put("total", total);
    	mReturn.put("totalPages", totalPages);
    	mReturn.put("dataPerPage", dataPerPage);
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
	
	
	/**
	 * �޷� ���� �ۼ��ϱ�
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/studyManagement/calendarWrite.do", method = RequestMethod.GET)
	public String calendarWritePopup(HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� ������ �� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		return "jsp/studyManagement/calendarWrite";
	}
	
	
	
}
 