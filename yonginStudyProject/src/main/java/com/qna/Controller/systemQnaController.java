package com.qna.Controller;


import java.util.HashMap;
import java.util.List;
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

import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;
import com.login.VO.userInfoVO;
import com.notice.VO.boardVO;
import com.qna.Service.qnaService;

@Controller
public class systemQnaController {

	@Resource(name="qnaService") 
	private qnaService qnaService;

	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	/*��Ʈ�ѷ� �̸��̶� ����*/
	private static final Logger logger = LoggerFactory.getLogger(systemQnaController.class);
	
	/**
	 * ���͵� �� ���� Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/systemQna.do", method = RequestMethod.GET)
	public String systemQnaForm(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("qnaStatus");
		

		model.addAttribute("qnaStatusArray", codeResult);
		
		return "jsp/qna/systemQna";
	}

	/**
	 * QnA��ȸ
	 * @param boardVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/systemQna/selectQnaList.json", method = RequestMethod.POST)
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
    	
    	int total = qnaService.selectQnaListToCnt(boardVO); // �� �� ���������� Ȯ��
    	int totalPages = (int)Math.ceil(total / (double)dataPerPage); // ������� �߰�
		
		/*** ����¡(��) ***/
    	
    	List<boardVO> ltResult = qnaService.selectQnaList(boardVO);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "success");
			mReturn.put("message", "Qna ����� �����ϴ�.");
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