package com.studyManagement.Controller;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.login.VO.userInfoVO;
import com.notice.VO.boardVO;
import com.studyManagement.Service.studyManagementService;

@Controller
public class studyFreeNoticeController {
	@Resource(name="studyManagementService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private studyManagementService studyManagementService;
	
	/*��Ʈ�ѷ� �̸��̶� ����*/
	private static final Logger logger = LoggerFactory.getLogger(studyFreeNoticeController.class);
	/**
	 * ���͵� �� ���� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/studyfreenotice.do", method = RequestMethod.GET)
	public String studyFreeNoticeControllerForm(Locale locale, Model model, HttpSession session) throws Exception {
		/*�ǵ��ʿ���� ������ ���°�*/
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		return "jsp/studyManagement/studyFreeNotice";
	}
	
	/**
	 * ���͵� �����Խ��� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/notice/selectStudyFreeNoticeList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectSystemNoticeList(@RequestBody boardVO boardVO) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		/*** ����¡(����) ***/
		int dataPerPage = 12; //�׸��� �� �������� ǥ���� ������ ��
    	int page = Integer.parseInt(boardVO.getPage()); //�������� ����
    	
    	int first = page * dataPerPage + 1; //������� �߰�
    	int last = first + dataPerPage - 1; //������� �߰�
    	
    	boardVO.setFirst(first); //������� �߰�
    	boardVO.setLast(last);   //������� �߰�
    	
    	int total = studyManagementService.selectStudyFreeNoticeListToCnt(boardVO); // �� �� ���������� Ȯ��
    	int totalPages = (int)Math.ceil(total / (double)dataPerPage); // ������� �߰�
		
		/*** ����¡(��) ***/
    	
    	List<boardVO> ltResult = studyManagementService.selectStudyFreeNoticeList(boardVO);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "success");
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
}