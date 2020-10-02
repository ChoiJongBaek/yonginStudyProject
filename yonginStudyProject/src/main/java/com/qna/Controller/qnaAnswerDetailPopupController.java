package com.qna.Controller;

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
import com.commonFunction.Service.fileService;
import com.login.VO.userInfoVO;
import com.notice.VO.boardVO;
import com.qna.Service.qnaService;

@Controller
public class qnaAnswerDetailPopupController {

	@Resource(name="qnaService")
	private qnaService qnaService;
	
	@Resource(name="fileService")
	private fileService fileService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	/**
	 * QnA ��� ���� �˾� 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/systemQna/qnaAnswerDetailPopup.do", method = RequestMethod.GET)
	public String qnaAnswerDetailPopup(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("boardVO", new boardVO());
		return "jsp/qna/qnaAnswerDetailPopup"; 
	}
	
	/**
	 * QnA ��� �� ��ȸ
	 * @param boardCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/systemQna/selectQnaAnswerDetail.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectQnaAnswerDetail(@RequestBody String boardCode) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(boardCode == null || boardCode.equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		boardVO boardVO = qnaService.selectQnaAnswerDetail(boardCode);
		
		if(boardVO == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		List<Map<String, Object>> fileList = fileService.selectFileList(boardVO.getBoardCode());
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ��ȸ�Ͽ����ϴ�.");
		mReturn.put("boardInfo", boardVO);
		mReturn.put("fileList", fileList);
		
		return mReturn;
	}
	
	
	@RequestMapping(value="/systemQna/deleteQnaAnswer.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteQna(@RequestBody boardVO boardVO, HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(boardVO.getHgrnkBoardCode().equals("") || boardVO.getBoardCode().equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		boardVO boardVO2 = qnaService.selectQnaDetail(boardVO.getBoardCode());
		
		if(boardVO2 == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
		if(!(boardVO2.getRgstusCode().equals(user.getUserCode()))) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �����ϴ�.");
			
			return mReturn;
		}
		
		qnaService.deleteQnaAnswer(boardVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� �����Ͽ����ϴ�.");
		
		return mReturn;
	}
}