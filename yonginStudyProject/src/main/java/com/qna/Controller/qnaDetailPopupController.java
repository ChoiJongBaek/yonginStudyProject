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
import com.commonCode.VO.commonCodeVO;
import com.commonFunction.Service.fileService;
import com.login.VO.userInfoVO;
import com.notice.VO.boardVO;
import com.qna.Service.qnaService;

@Controller
public class qnaDetailPopupController {

	@Resource(name="qnaService")
	private qnaService qnaService;
	
	@Resource(name="fileService")
	private fileService fileService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	@RequestMapping(value = "/systemQna/qnaDetailPopup.do", method = RequestMethod.GET)
	public String studyInfoDetailPopup(Model model, HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("qnaStatus");
		
		//model ������ �����͸� ��� jsp�� ����
		model.addAttribute("boardVO", new boardVO());
		model.addAttribute("qnaStatusArray", codeResult);
		
		return "jsp/qna/qnaDetailPopup"; 
	}
	
	/**
	 * QnA �� ���� 
	 * @param systemNoticeCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/systemQna/selectQnaDetail.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectQnaDetail(@RequestBody String boardCode) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(boardCode == null || boardCode.equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		boardVO boardVO = qnaService.selectQnaDetail(boardCode);
		
		if(boardVO == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		List<Map<String, Object>> fileList = fileService.selectFileList(boardCode);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� ��ȸ�Ͽ����ϴ�.");
		mReturn.put("boardInfo", boardVO);
		mReturn.put("fileList", fileList);
		
		return mReturn;
	}
	
	/**
	 * QnA����
	 * @param boardCode
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/systemQna/deleteQna.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteQna(@RequestBody String boardCode, HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(boardCode == null || boardCode.equals("")) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		boardVO boardVO = qnaService.selectQnaDetail(boardCode);
		
		if(boardVO == null) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
			
			return mReturn;
		}
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
		if(!(boardVO.getRgstusCode().equals(user.getUserCode()))) {
			mReturn.put("result","fail");
			mReturn.put("message", "������ �����ϴ�.");
			
			return mReturn;
		}
		
		qnaService.deleteQna(boardCode);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� �����Ͽ����ϴ�.");
		
		return mReturn;
	}
	
}