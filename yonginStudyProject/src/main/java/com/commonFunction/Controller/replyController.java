package com.commonFunction.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commonFunction.Service.replyService;
import com.commonFunction.VO.replyVO;
import com.login.VO.userInfoVO;

@Controller
public class replyController {
	
	@Resource(name="replyService")
	private replyService replyService;
	 
	/**
	 * ��� ����
	 * @param replyVO
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reply/insertReply.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertReply(@RequestBody replyVO replyVO, HttpSession session) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(replyVO.getBoardCode() == null || replyVO.getBoardCode().equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "Board �ڵ尡 �����ϴ�.");
			
			return mReturn;
		}
		if(replyVO.getReplyText().equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "��� ������ �Է����ּ���.");
			
			return mReturn;
		}
		
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		replyVO.setRgstusId(user.getUserCode());
		
		replyService.insertReply(replyVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "����� �ۼ��Ͽ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ��� ��ȸ
	 * @param boardCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reply/selectReplyList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectReplyList(@RequestBody String boardCode) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(boardCode == null || boardCode.equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "Board �ڵ尡 �����ϴ�.");
			
			return mReturn;
		}
		
		List<replyVO> ltResult = replyService.selectReplyList(boardCode);
		
		mReturn.put("result", "success");
		mReturn.put("message", "����� ��ȸ�Ͽ����ϴ�.");
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
	
	/**
	 * ��� ����
	 * @param replyCode
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reply/deleteReply.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteReply(@RequestBody String replyCode, HttpSession session) throws Exception {
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(replyCode == null || replyCode.equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "��� �ڵ尡 �����ϴ�.");
			
			return mReturn;
		}
		
		/** ��� �ۼ��ڿ� ���� ������ ��(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		replyVO data = replyService.selectReplyWithId(replyCode);
		
		if(!(data.getRgstusCode().equals(user.getUserCode()))) {
			mReturn.put("result", "fail");
			mReturn.put("message", "������ �����ϴ�.");
			
			return mReturn;
		}
		/** ��� �ۼ��ڿ� ���� ������ ��(��) **/
		
		replyService.deleteReply(replyCode);
		
		mReturn.put("result", "success");
		mReturn.put("message", "����� ���������� �����߽��ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ��� ����
	 * @param replyVO
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reply/updateReply.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateReply(@RequestBody replyVO replyVO, HttpSession session) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(replyVO.getReplyText().equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "��� ������ �Է����ּ���.");
			
			return mReturn;
		}
		if(replyVO.getReplyCode().equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "��� �ڵ尡 �����ϴ�.");
			
			return mReturn;
		}
		
		/** ��� �ۼ��ڿ� ���� ������ ��(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		replyVO data = replyService.selectReplyWithId(replyVO.getReplyCode());
		
		if(!(data.getRgstusCode().equals(user.getUserCode()))) {
			mReturn.put("result", "fail");
			mReturn.put("message", "������ �����ϴ�.");
			
			return mReturn;
		}
		/** ��� �ۼ��ڿ� ���� ������ ��(��) **/
		
		replyService.updateReply(replyVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "������ �Ϸ�Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ��� �� ��ȸ
	 * @param replyCode
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reply/selectReplyWithId.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectReplyWithId(@RequestBody String replyCode, HttpSession session) throws Exception {
		
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
		
		if(replyCode.equals("")) {
			mReturn.put("result", "fail");
			mReturn.put("message", "��� �ڵ尡 �����ϴ�.");
			
			return mReturn;
		}
		replyVO data = replyService.selectReplyWithId(replyCode);
		
		/** ��� �ۼ��ڿ� ���� ������ ��(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		
		if(!(data.getRgstusCode().equals(user.getUserCode()))) {
			mReturn.put("result", "fail");
			mReturn.put("message", "������ �����ϴ�.");
			
			return mReturn;
		}
		/** ��� �ۼ��ڿ� ���� ������ ��(��) **/
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ�� �����Ͽ����ϴ�.");
		mReturn.put("replyVO", data);
		
		return mReturn;
	}
	
	
	/**
	 * ��� �ޱ� �˾�â
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reply/replyOnReplyForm.do", method = RequestMethod.POST)
	public String replyOnReplyForm(HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		return "jsp/common/replyOnReply";
	}
	
	/**
	 * ��� ���� �˾�â
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reply/updateReplyForm.do", method = RequestMethod.POST)
	public String updateReplyForm(HttpSession session) throws Exception {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		return "jsp/common/updateReply";
	}
	
}