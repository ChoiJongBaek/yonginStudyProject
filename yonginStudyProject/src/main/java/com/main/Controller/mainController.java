package com.main.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.commonFunction.Controller.yonginFunction;
import com.login.VO.userInfoVO;
import com.main.Service.mainService;
import com.main.VO.calendarVO;
import com.main.VO.studyInfoVO;
import com.notice.VO.boardVO;
/**
 * Handles requests for the application home page.
 */
@Controller
public class mainController {
	@Resource(name="mainService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private mainService mainService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	private static final Logger logger = LoggerFactory.getLogger(mainController.class);
	
	/**
	 * �α��� ȭ�� Controller
	 * @throws Exception 
	 */
	@RequestMapping(value = "/main/main.do", method = RequestMethod.GET)
	public String login(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		
		List<commonCodeVO> codeResult1 = commonCodeService.selectCommonCodeList("studyTopic");
		List<commonCodeVO> codeResult2 = commonCodeService.selectCommonCodeList("calendarType");

		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("studyTopicArray", codeResult1);
		model.addAttribute("calendarType", codeResult2);
		
		return "jsp/main/main";
	}
	
	/**
	 * ���͵� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/main/selectStudyList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyList(HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
		List<studyInfoVO> ltResult = mainService.selectStudyList(user.getUserCode());
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���͵� ����� �����ϴ�.");
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ �����Ͽ����ϴ�.");
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
	
	/**
	 * ���͵� �������� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/main/selectStudyNoticeList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyNoticeList(@RequestBody boardVO boardVO, HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		boardVO.setCurrentUserCode(user.getUserCode());
		
		/*** ����¡(����) ***/
		int dataPerPage = 10; //�׸��� �� �������� ǥ���� ������ ��
    	int page = Integer.parseInt(boardVO.getPage()); //�������� ����
    	
    	int first = page * dataPerPage + 1; //������� �߰�
    	int last = first + dataPerPage - 1; //������� �߰�
    	
    	boardVO.setFirst(first); //������� �߰�
    	boardVO.setLast(last);   //������� �߰�
    	
    	int total = mainService.selectStudyNoticeListToCnt(boardVO); // �� �� ���������� Ȯ��
    	int totalPages = (int)Math.ceil(total / (double)dataPerPage); // ������� �߰�
		
		/*** ����¡(��) ***/
    	
    	List<boardVO> ltResult = mainService.selectStudyNoticeList(boardVO);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "�������� ����� �����ϴ�.");
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
	 * �ڱⰡ ������ ���͵��� ���� ��ȸ
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/main/searchMyStudyCalendar.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> searchMyStudyCalendar(@RequestBody calendarVO calendarVO, HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		calendarVO.setUserCode(user.getUserCode());
		
		List<calendarVO> ltResult = mainService.searchMyStudyCalendar(calendarVO);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���� ����� �����ϴ�.");
		}
		
		// �ð� �������� ��ȯ
		for(int i=0;i<ltResult.size();i++) {
			calendarVO vo = ltResult.get(i);
			
			// '-' �߰�
    		String startDt = yonginFunction.nullConvert(vo.getStartDt());
    		vo.setStartDt(yonginFunction.addMinusChar(startDt));
    		String endDt = yonginFunction.nullConvert(vo.getEndDt());
    		vo.setEndDt(yonginFunction.addMinusChar(endDt));
    		// ':' �߰�
            String startHm = vo.getStartHm();
            vo.setStartHm(yonginFunction.addColonChar(startHm));
            String endHm = vo.getEndHm();
            vo.setEndHm(yonginFunction.addColonChar(endHm));
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ �����Ͽ����ϴ�.");
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
	
	/**
	 * �޷� �˾� �󼼺���
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/main/calendarDetailPopup.do", method = RequestMethod.POST)
	public String calendarDetailPopup(HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� ������ �� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		return "jsp/main/calendarDetailPopup";
	}
	
	
	
}
	
	
	