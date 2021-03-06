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
	@Resource(name="studyManagementService") // 해당 서비스가 리소스임을 표시합니다.
	private studyManagementService studyManagementService;

	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;

	/**
	 *  스터디 관리 페이지 Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/studyManagement/studyManage.do", method = RequestMethod.POST)
	public String studyManageForm(Model model, HttpSession session) throws Exception {
		/** 세션에 유저가 정상적으로 등록되어 있지 않다면 로그인 페이지로 이동(시작) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		
		/** 세션에 유저가 정상적으로 등록되어 있지 않다면 로그인 페이지로 이동(끝) **/
		
		List<commonCodeVO> codeResult1 = commonCodeService.selectCommonCodeList("SAFStatus");
		List<commonCodeVO> codeResult2 = commonCodeService.selectCommonCodeList("studyTopic");
		List<commonCodeVO> codeResult3 = commonCodeService.selectCommonCodeList("studyAuthority");
		
		model.addAttribute("applicationFormStatusArray", codeResult1);
		model.addAttribute("studyTopicArray", codeResult2);
		model.addAttribute("studyAuthorityArray", codeResult3);
		
		return "jsp/studyManagement/studyManage";
	}
	
	/**
	 * 스터디 멤버 리스트 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagemetAdmin/selectStudyMainMemberList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyMemberList(@RequestBody userInStudyVO userInStudyVO) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		
		/*** 페이징(시작) ***/
		int dataPerPage = 10; //그리드 한 페이지에 표시할 데이터 수
    	int page = Integer.parseInt(userInStudyVO.getPage()); //페이지별 변경
    	
    	int first = page * dataPerPage + 1; //변경없이 추가
    	int last = first + dataPerPage - 1; //변경없이 추가
    	
    	userInStudyVO.setFirst(first); //변경없이 추가
    	userInStudyVO.setLast(last);   //변경없이 추가
    	
    	int total = studyManagementService.selectStudyMemberListToCnt(userInStudyVO); // 총 몇 페이지인지 확인
    	int totalPages = (int)Math.ceil(total / (double)dataPerPage); // 변경없이 추가
		
    	/*** 페이징(끝) ***/

    	List<userInStudyVO> ltResult = studyManagementService.selectStudyMemberList(userInStudyVO);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "success");
			mReturn.put("message", "스터디원 목록이 없습니다.");
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "조회 성공하였습니다.");
		mReturn.put("total", total);
    	mReturn.put("totalPages", totalPages);
    	mReturn.put("dataPerPage", dataPerPage);
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
	
	/**
	 * 스터디 신청서 조회하기
	 * @param studyApplicationFormUserVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagemetAdmin/selectStudyApplicationForm.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyApplicationForm(@RequestBody studyApplicationFormUserVO studyApplicationFormUserVO) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		
		/*** 페이징(시작) ***/
		int dataPerPage = 5; 
    	int page = Integer.parseInt(studyApplicationFormUserVO.getPage()); 
    	
    	int first = page * dataPerPage + 1; 
    	int last = first + dataPerPage - 1; 
    	
    	studyApplicationFormUserVO.setFirst(first); 
    	studyApplicationFormUserVO.setLast(last);   
    	
    	int total = studyManagementService.selectStudyApplicationFormToCnt(studyApplicationFormUserVO);
    	int totalPages = (int)Math.ceil(total / (double)dataPerPage); 
		
    	/*** 페이징(끝) ***/

    	List<studyApplicationFormUserVO> ltResult = studyManagementService.selectStudyApplicationForm(studyApplicationFormUserVO);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "success");
			mReturn.put("message", "신청서 목록이 없습니다.");
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "조회 성공하였습니다.");
		mReturn.put("total", total);
    	mReturn.put("totalPages", totalPages);
    	mReturn.put("dataPerPage", dataPerPage);
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
	
	/**
	 * 신청서 승인
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
			mReturn.put("message", "필요한 정보를 불러오는데 실패했습니다.");
			
			return mReturn;
		}
		
		int studyTotalCount = studyManagementService.selectStudyCountToCnt(studyApplicationFormUserVO.getStudyCode());
		int currentTotalCount = studyManagementService.selectStudyMemberToCnt(studyApplicationFormUserVO.getStudyCode());

		if(currentTotalCount == studyTotalCount) {
			mReturn.put("result", "fail");
			mReturn.put("message", "스터디 정원을 초과합니다.");
			
			return mReturn;
		}
		
    	studyManagementService.approveStudyForm(studyApplicationFormUserVO);
		
		
		mReturn.put("result", "success");
		mReturn.put("message", "성공적으로 승인되었습니다.");
		
		return mReturn;
	}
	
	/**
	 * 신청서 거부
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
			mReturn.put("message", "필요한 정보를 불러오는데 실패했습니다.");
			
			return mReturn;
		}
		
    	studyManagementService.rejectStudyForm(studyApplicationFormUserVO);
		
		
		mReturn.put("result", "success");
		mReturn.put("message", "성공적으로 거부되었습니다.");
		
		return mReturn;
	}
} 
