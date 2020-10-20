package com.main.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;
import com.login.VO.userInfoVO;
import com.main.Service.adminService;
import com.main.VO.studyInfoVO;
import com.study.Service.studyService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class adminPageController {
	@Resource(name="adminService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private adminService adminService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	@Resource(name="studyService")
	private studyService studyService;
	
	private static final Logger logger = LoggerFactory.getLogger(adminPageController.class);
	
	/**
	 * ���������� �˾� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/openAdminPage.do", method = RequestMethod.GET)
	public String openAdminPage(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		
		// ������ ������ ���� ��� �α��� �������� ����(������ ������ �ʿ��� ���)
		if(!user.getUserIsAdmin().equals("Y")) {
			session.invalidate();
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("studyTopic");
		
		model.addAttribute("studyTopicArray", codeResult);
		return "jsp/main/adminPage";
	}
	
	/**
	 * ����� ����Ʈ ��ȸ
	 * @param userInfoVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/adminPage/selectUserList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectUserList(@RequestBody userInfoVO userInfoVO, HttpServletRequest request) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		/*** ����¡(����) ***/
		int dataPerPage = 6; //�׸��� �� �������� ǥ���� ������ ��
    	int page = Integer.parseInt(userInfoVO.getPage()); //�������� ����
    	
    	int first = page * dataPerPage + 1; //������� �߰�
    	int last = first + dataPerPage - 1; //������� �߰�
    	
    	userInfoVO.setFirst(first); //������� �߰�
    	userInfoVO.setLast(last);   //������� �߰�
    	
    	int total = adminService.selectUserListToCnt(userInfoVO); // �� �� ���������� Ȯ��
    	int totalPages = (int)Math.ceil(total / (double)dataPerPage); // ������� �߰�
		
		/*** ����¡(��) ***/
    	
		List<userInfoVO> ltResult = adminService.selectUserList(userInfoVO);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "����� ����� �����ϴ�.");
			
			return mReturn;
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
	 * ������ ���������� ���͵� ��� ��ȸ
	 * @param studyInfoVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/adminPage/selectStudyList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyList(@RequestBody studyInfoVO studyInfoVO, HttpServletRequest request) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		/*** ����¡(����) ***/
		int dataPerPage = 6; //�׸��� �� �������� ǥ���� ������ ��
    	int page = Integer.parseInt(studyInfoVO.getPage()); //�������� ����
    	
    	int first = page * dataPerPage + 1; //������� �߰�
    	int last = first + dataPerPage - 1; //������� �߰�
    	
    	studyInfoVO.setFirst(first); //������� �߰�
    	studyInfoVO.setLast(last);   //������� �߰�
    	
    	int total = studyService.selectStudyListToCnt(studyInfoVO); // �� �� ���������� Ȯ��
    	int totalPages = (int)Math.ceil(total / (double)dataPerPage); // ������� �߰�
		
		/*** ����¡(��) ***/
    	
		List<studyInfoVO> ltResult = studyService.selectStudyList(studyInfoVO);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���͵� ����� �����ϴ�.");
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
	 * ����� �߹�
	 * @param userCode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/adminPage/kickUser.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> kickUser(@RequestBody String userCode, HttpServletRequest request) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		if(userCode == null || userCode.equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "����ڰ� ���õ��� �ʾҽ��ϴ�.");
		}
		
		adminService.kickUser(userCode);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���� ������ ����Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ������ ���� ���
	 * @param userCode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/adminPage/cancleAdminUser.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancleAdminUser(@RequestBody String userCode, HttpServletRequest request) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		if(userCode == null || userCode.equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "����ڰ� ���õ��� �ʾҽ��ϴ�.");
		}
		
		adminService.cancleAdminUser(userCode);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���� ������ ����Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ������ ���� �Ӹ�
	 * @param userCode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/adminPage/setAdminUser.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setAdminUser(@RequestBody String userCode, HttpServletRequest request) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		if(userCode == null || userCode.equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "����ڰ� ���õ��� �ʾҽ��ϴ�.");
		}
		
		adminService.setAdminUser(userCode);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���� ������ ����Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ���͵� ����
	 * @param studyCode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/adminPage/deleteStudy.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStudy(@RequestBody String studyCode, HttpServletRequest request) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		if(studyCode == null || studyCode.equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���͵� ���õ��� �ʾҽ��ϴ�.");
		}
		
		adminService.deleteStudy(studyCode);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���� ������ ����Ǿ����ϴ�.");
		
		return mReturn;
	}
}
