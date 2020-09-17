package com.message.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commonCode.Service.commonCodeService;
import com.login.VO.userInfoVO;
import com.message.Service.messageService;
import com.message.VO.messageInfoVO;

@Controller
public class sendMessageListController {

	@Resource(name="messageService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private messageService messageService;
	
	@Resource(name="commonCodeService")
	private commonCodeService commonCodeService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(messageListController.class);
	
	/**
	 * ������ Mapping
	 */
	@RequestMapping(value = "/sendMessageList.do", method = RequestMethod.GET)
	public String sendMessageListForm(Locale locale, HttpSession session) {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		
		return "jsp/message/sendMessageList";
	}
	
	/**
	 * �޽��� ����
	 * @param messageInfoVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteSendMessage.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> messageDeleteAjaxFunction(@RequestBody messageInfoVO messageInfoVO) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		
		//���������� �߰�
		messageService.deleteSendMessage(messageInfoVO);
		
		mReturn.put("result", "success");
		mReturn.put("message", "���������� �����Ǿ����ϴ�.");
		
		return mReturn;
	}
	
	/**
	 * ���� ���� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectSendMessageList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectSendMessageList(HttpSession session) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		userInfoVO user = (userInfoVO) session.getAttribute("user");
		String userCode = user.getUserCode();
		System.out.println("����� : "+userCode);
		List<messageInfoVO> ltResult = messageService.selectSendMessageList(userCode);
		
		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���� ����� �����ϴ�.");
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ �����Ͽ����ϴ�.");
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}
}
