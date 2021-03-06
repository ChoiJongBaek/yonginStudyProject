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
import com.commonFunction.Controller.yonginFunction;
import com.login.VO.userInfoVO;
import com.main.VO.calendarVO;
import com.main.VO.userInStudyVO;
import com.studyManagement.Service.studyMainService;
import com.studyManagement.Service.studyManagementService;

@Controller
public class studyMainController {
	@Resource(name="studyManagementService") // 해당 서비스가 리소스임을 표시합니다.
	private studyManagementService studyManagementService;

	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	@Resource(name="studyMainService") // 해당 서비스가 리소스임을 표시합니다.
	private studyMainService studyMainService;
	/**
	 * 스터디 전용 페이지 Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/studyManagement/studyMain.do", method = RequestMethod.POST)
	public String studyMainForm(Model model, HttpSession session) throws Exception {
		/** 세션에 유저가 정상적으로 등록되어 있지 않다면 로그인 페이지로 이동(시작) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		
		/** 세션에 유저가 정상적으로 등록되어 있지 않다면 로그인 페이지로 이동(끝) **/
		
		
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
	 * 접속자의 스터디 권한 가져오기
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
			mReturn.put("message", "오류가 발생하였습니다.");
			
			return mReturn;
		}
		
		String result = studyMainService.selectStudyUserInfo(userinfo);
		
		mReturn.put("result", "success");
		mReturn.put("message", "조회 성공하였습니다.");
		mReturn.put("userinfo", result);
		
		return mReturn;
	}
	
	
	/**
	 * 스터디 멤버 리스트 조회
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagemet/selectStudyMainMemberList", method = RequestMethod.POST)
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
	 * 해당 스터디 일정 조회
	 * @param studyCode
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/selectStudyCalendar.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyCalendar(@RequestBody calendarVO calendarVO, HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		List<calendarVO> ltResult = studyMainService.selectStudyCalendar(calendarVO);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "일정 목록이 없습니다.");
		}
		
		// 시간 형식으로 변환
		for(int i=0;i<ltResult.size();i++) {
			calendarVO vo = ltResult.get(i);
			
			// '-' 추가
    		String startDt = yonginFunction.nullConvert(vo.getStartDt());
    		vo.setStartDt(yonginFunction.addMinusChar(startDt));
    		String endDt = yonginFunction.nullConvert(vo.getEndDt());
    		vo.setEndDt(yonginFunction.addMinusChar(endDt));
    		// ':' 추가
            String startHm = vo.getStartHm();
            vo.setStartHm(yonginFunction.addColonChar(startHm));
            String endHm = vo.getEndHm();
            vo.setEndHm(yonginFunction.addColonChar(endHm));
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "조회 성공하였습니다.");
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
	
}
 