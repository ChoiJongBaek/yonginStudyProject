package com.notice.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.login.VO.userInfoVO;
import com.main.VO.studyInfoVO;
import com.notice.Service.systemNoticeService;
import com.notice.VO.moreNoticeInfoVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class moreNoticeController {
	@Resource(name="systemNoticeService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
	private systemNoticeService systemNoticeService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(moreNoticeController.class);
	
	/**
	 * �������� ������ Mapping
	 */
	@RequestMapping(value = "/moreNotice.do", method = RequestMethod.GET)
	public String MoreNoticeForm(HttpSession session) {
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(����) **/
		userInfoVO user = (userInfoVO) session.getAttribute("user");

		if(user == null) {
			return "jsp/login/login";
		}
		/** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
		 
		return "jsp/notice/moreNotice";
	}
	
	/**
	 * �ý��� �������� ����Ʈ ��ȸ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/notice/selectSystemNoticeList.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectStudyList(HttpServletRequest request) throws Exception {
	      
		HashMap<String, Object> mReturn = new HashMap<String, Object>();
	      
		List<moreNoticeInfoVO> ltResult = systemNoticeService.selectSystemNoticeList();

		if(ltResult.size() < 1) {
			mReturn.put("result", "fail");
			mReturn.put("message", "���͵� ����� �����ϴ�.");
		}
		
		mReturn.put("result", "success");
		mReturn.put("message", "��ȸ �����Ͽ����ϴ�.");
		mReturn.put("resultList", ltResult);
		
		return mReturn;
	}

}
