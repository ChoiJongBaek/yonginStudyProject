package com.studyManagement.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.login.VO.userInfoVO;
import com.notice.VO.boardVO;
import com.studyManagement.Service.studyNoticeService;

@Controller
public class studyNoticeController {
	 @Resource(name="studyNoticeService") 
	  private studyNoticeService studyNoticeService;
	
	/*
	�����ڵ� �ʿ�� ���
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	*/
	
	/*��Ʈ�ѷ� �̸��̶� ����*/
	private static final Logger logger = LoggerFactory.getLogger(studyNoticeController.class);
	
	/**
	 * ���͵� ���� �������� ������
	 * @throws Exception 
	 */
	@RequestMapping(value = "/studynotice.do", method = RequestMethod.POST)
	public String studyNoticeForm(HttpSession session) /*throws Exception*/ {
		/*�ǵ��ʿ���� ������ ���°�*/
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		/*���͵����ȿ� ���õ� �����͸�  db���� �������� �װ� coderesult�� ����*/
		/*List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("studyTopic");*/
		
		//model ������ �����͸� ��� jsp�� ����
		/*coderesult�� moodel�� �־��*/
		/*model.addAttribute("studyTopicArray", codeResult);*/
		
		return "jsp/studyManagement/studyNotice";
	}
	
	/**
	 * ���͵� �������� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/selectStudyNoticeList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyNoticeList(@RequestBody boardVO boardVO) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		/*** ����¡(����) ***/
		int dataPerPage = 12; //�׸��� �� �������� ǥ���� ������ ��
    	int page = Integer.parseInt(boardVO.getPage()); //�������� ����
    	
    	int first = page * dataPerPage + 1; //������� �߰�
    	int last = first + dataPerPage - 1; //������� �߰�
    	
    	boardVO.setFirst(first); //������� �߰�
    	boardVO.setLast(last);   //������� �߰�
    	
    	int total = studyNoticeService.selectStudyNoticeListToCnt(boardVO); // �� �� ���������� Ȯ��
    	int totalPages = (int)Math.ceil(total / (double)dataPerPage); // ������� �߰�
		
		/*** ����¡(��) ***/
    	
    	List<boardVO> ltResult = studyNoticeService.selectStudyNoticeList(boardVO);
		
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
	
	/**
	 * ���͵� �������� ����
	 * @param boardVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/studyManagement/deleteStudyNotice.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMessageTo(@RequestBody boardVO boardVO, HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		// ������ ������ ���� ��� ���� �޽��� �߻�
		if(!user.getUserIsAdmin().equals("Y")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "������ �����ϴ�.");
			
			return mReturn;
		}
				
		studyNoticeService.deleteStudyNotice(boardVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� �����Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * �������� �ۼ��˾�
	 * @param session
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/studyManagement/writeStudyNotice.do", method = RequestMethod.GET)
	public String writeStudyNoticePopup(HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� ������ �� �̵�(����) **/
		/*userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		/*return "jsp/studyManagement/writeStudyNotice";
	}*/
	
	
}