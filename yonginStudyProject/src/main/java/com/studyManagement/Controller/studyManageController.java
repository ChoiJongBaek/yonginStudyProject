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
import com.study.VO.studyApplicationFormUserVO;
import com.studyManagement.Service.studyManagementService;

@Controller
public class studyManageController {
	@Resource(name="studyManagementService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyManagementService studyManagementService;

	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;

	/**
	 *  ���͵� ���� ������ Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/studyManagement/studyManage.do", method = RequestMethod.POST)
	public String studyManageForm(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		List<commonCodeVO> codeResult1 = commonCodeService.selectCommonCodeList("SAFStatus");
		List<commonCodeVO> codeResult2 = commonCodeService.selectCommonCodeList("studyTopic");
		List<commonCodeVO> codeResult3 = commonCodeService.selectCommonCodeList("studyAuthority");
		
		model.addAttribute("applicationFormStatusArray", codeResult1);
		model.addAttribute("studyTopicArray", codeResult2);
		model.addAttribute("studyAuthorityArray", codeResult3);
		
		return "jsp/studyManagement/studyManage";
	}
	
	/**
	 * ���͵� ��� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagemetAdmin/selectStudyMainMemberList", method = RequestMethod.POST)
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
	 * ���͵� ��û�� ��ȸ�ϱ�
	 * @param studyApplicationFormUserVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagemetAdmin/selectStudyApplicationForm.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyApplicationForm(@RequestBody studyApplicationFormUserVO studyApplicationFormUserVO) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		
		/*** ����¡(����) ***/
		int dataPerPage = 5; 
    	int page = Integer.parseInt(studyApplicationFormUserVO.getPage()); 
    	
    	int first = page * dataPerPage + 1; 
    	int last = first + dataPerPage - 1; 
    	
    	studyApplicationFormUserVO.setFirst(first); 
    	studyApplicationFormUserVO.setLast(last);   
    	
    	int total = studyManagementService.selectStudyApplicationFormToCnt(studyApplicationFormUserVO);
    	int totalPages = (int)Math.ceil(total / (double)dataPerPage); 
		
    	/*** ����¡(��) ***/

    	List<studyApplicationFormUserVO> ltResult = studyManagementService.selectStudyApplicationForm(studyApplicationFormUserVO);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "success");
			mReturn.put("message", "��û�� ����� �����ϴ�.");
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
	 * ��û�� ����
	 * @param studyApplicationFormUserVO
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagemetAdmin/approveStudyForm.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approveStudyForm(@RequestBody studyApplicationFormUserVO studyApplicationFormUserVO, HttpSession session) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
		studyApplicationFormUserVO.setUpdtusId(user.getUserCode());
		
		if(studyApplicationFormUserVO.getApplicationFormCode().equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "�ʿ��� ������ �ҷ����µ� �����߽��ϴ�.");
			
			return mReturn;
		}
		
		int studyTotalCount = studyManagementService.selectStudyCountToCnt(studyApplicationFormUserVO.getStudyCode());
		int currentTotalCount = studyManagementService.selectStudyMemberToCnt(studyApplicationFormUserVO.getStudyCode());

		if(currentTotalCount == studyTotalCount) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���͵� ������ �ʰ��մϴ�.");
			
			return mReturn;
		}
		
    	studyManagementService.approveStudyForm(studyApplicationFormUserVO);
		
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ���εǾ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ��û�� �ź�
	 * @param studyApplicationFormUserVO
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagemetAdmin/rejectStudyForm.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> rejectStudyForm(@RequestBody studyApplicationFormUserVO studyApplicationFormUserVO, HttpSession session) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
		studyApplicationFormUserVO.setUpdtusId(user.getUserCode());
		
		if(studyApplicationFormUserVO.getApplicationFormCode().equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "�ʿ��� ������ �ҷ����µ� �����߽��ϴ�.");
			
			return mReturn;
		}
		
    	studyManagementService.rejectStudyForm(studyApplicationFormUserVO);
		
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� �źεǾ����ϴ�.");
		
		return mReturn;
	}
} 
