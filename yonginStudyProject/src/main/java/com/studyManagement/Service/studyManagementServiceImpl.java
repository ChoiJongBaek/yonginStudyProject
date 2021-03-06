package com.studyManagement.Service; 

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.commonFunction.Controller.FileUtilsController;
import com.commonFunction.DAO.fileDAO;
import com.commonFunction.DAO.replyDAO;
import com.login.VO.userInfoVO;
import com.main.VO.studyInfoVO;
import com.main.VO.userInStudyVO;
import com.message.DAO.messageDAO;
import com.message.VO.messageInfoVO;
import com.notice.VO.boardVO;
import com.study.VO.studyApplicationFormUserVO;
import com.studyManagement.DAO.studyManagementDAO;

@Service("studyManagementService")
public class studyManagementServiceImpl implements studyManagementService{
	@Autowired
	studyManagementDAO studyManagementDAO;
	
	@Autowired
	messageDAO messageDAO;
	
	@Autowired
	fileDAO fileDAO;
	
	@Autowired
	replyDAO replyDAO;
	
	@Override
	public List<userInStudyVO> selectStudyMemberList(userInStudyVO userInStudyVO){
		return studyManagementDAO.selectStudyMemberList(userInStudyVO);
	}
	
	@Override
	public int selectStudyMemberListToCnt(userInStudyVO userInStudyVO)	{
		return studyManagementDAO.selectStudyMemberListToCnt(userInStudyVO);
	}
	
	@Override
	public void writeStudyFreeNotice(boardVO data, MultipartHttpServletRequest mpRequest) throws Exception{ 
		studyManagementDAO.writeStudyFreeNotice(data);
		
		List<Map<String,Object>> list = FileUtilsController.parseInsertFileInfo(data, mpRequest); 
		int size = list.size();
		for(int i=0; i<size; i++){ 
			fileDAO.insertFile(list.get(i)); 
		}
	}
	
	@Override
	public int selectStudyFreeNoticeListToCnt(boardVO boardVO) {
		return studyManagementDAO.selectStudyFreeNoticeListToCnt(boardVO);
	}
	
	@Override
	public List<boardVO> selectStudyFreeNoticeList(boardVO boardVO){
		return studyManagementDAO.selectStudyFreeNoticeList(boardVO);
	}
	
	@Override
	public boardVO selectStudyFreeNoticeInfoDetail(String boardCode) throws Exception {
		studyManagementDAO.updateStudyFreeNoticeCnt(boardCode);
		return studyManagementDAO.selectStudyFreeNoticeInfoDetail(boardCode);
	}
	
	@Override
	public void reviseStudyFreeNotice(boardVO data, MultipartHttpServletRequest mpRequest) throws Exception{
		studyManagementDAO.reviseStudyFreeNotice(data);
		
		String[] files = data.getFileCodeDel();
		
		List<Map<String, Object>> list = FileUtilsController.parseUpdateFileInfo(data, files, mpRequest);
		Map<String, Object> tempMap = null;
		int size = list.size();
		for(int i = 0; i<size; i++) {
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")) {
				fileDAO.insertFile(tempMap);
			}else {
				fileDAO.updateFile(tempMap);
			}
		}
	}
	
	@Override
	public void deleteStudyFreeNotice(boardVO boardVO) throws Exception{
			String boardCode = boardVO.getBoardCode();
			studyManagementDAO.deleteStudyFreeNotice(boardCode);
			fileDAO.updateNFileWithDeleteBoard(boardCode);
			replyDAO.deleteReplyWithBoardCode(boardCode);
		
	}
	
	@Override
	public void writeStudyNotice(boardVO data, MultipartHttpServletRequest mpRequest) throws Exception{ 
		studyManagementDAO.writeStudyNotice(data);
		
		List<Map<String,Object>> list = FileUtilsController.parseInsertFileInfo(data, mpRequest); 
		int size = list.size();
		for(int i=0; i<size; i++){ 
			fileDAO.insertFile(list.get(i)); 
		}
	}
	
	@Override
	public int selectStudyNoticeListToCnt(boardVO boardVO) {
		return studyManagementDAO.selectStudyNoticeListToCnt(boardVO);
	}
	
	@Override
	public List<boardVO> selectStudyNoticeList(boardVO boardVO){
		return studyManagementDAO.selectStudyNoticeList(boardVO);
	}
	
	@Override
	public boardVO selectStudyNoticeInfoDetail(String boardCode) throws Exception {
		studyManagementDAO.updateStudyNoticeCnt(boardCode);
		return studyManagementDAO.selectStudyNoticeInfoDetail(boardCode);
	}
	
	@Override
	public void reviseStudyNotice(boardVO data, MultipartHttpServletRequest mpRequest) throws Exception{
		studyManagementDAO.reviseStudyNotice(data);
		
		String[] files = data.getFileCodeDel();
		
		List<Map<String, Object>> list = FileUtilsController.parseUpdateFileInfo(data, files, mpRequest);
		Map<String, Object> tempMap = null;
		int size = list.size();
		for(int i = 0; i<size; i++) {
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")) {
				fileDAO.insertFile(tempMap);
			}else {
				fileDAO.updateFile(tempMap);
			}
		}
	}
	
	@Override
	public void deleteStudyNotice(boardVO boardVO) throws Exception{
			String boardCode = boardVO.getBoardCode();
			studyManagementDAO.deleteStudyNotice(boardCode);
			fileDAO.updateNFileWithDeleteBoard(boardCode);
			replyDAO.deleteReplyWithBoardCode(boardCode);
		
	}
	
	@Override
	public userInfoVO selectStudyMemberManage(String userCode) throws Exception {
		return studyManagementDAO.selectStudyMemberManage(userCode);
	}
	
	@Override
	public void deportStudyMember(messageInfoVO data) throws Exception{
		messageDAO.sendMessage(data);
		studyManagementDAO.deportStudyMember(data);
	}

	@Override
	public List<studyApplicationFormUserVO> selectStudyApplicationForm(studyApplicationFormUserVO studyApplicationFormUserVO) throws Exception{
		return studyManagementDAO.selectStudyApplicationForm(studyApplicationFormUserVO);
	}
	
	@Override
	public int selectStudyApplicationFormToCnt(studyApplicationFormUserVO studyApplicationFormUserVO) throws Exception{
		return studyManagementDAO.selectStudyApplicationFormToCnt(studyApplicationFormUserVO);
	}
	
	@Override
	public void approveStudyForm(studyApplicationFormUserVO studyApplicationFormUserVO) throws Exception{
		studyManagementDAO.approveStudyForm(studyApplicationFormUserVO);
		studyManagementDAO.insertUserInStudy(studyApplicationFormUserVO);
	}
	
	@Override
	public void rejectStudyForm(studyApplicationFormUserVO studyApplicationFormUserVO) throws Exception{
		studyManagementDAO.rejectStudyForm(studyApplicationFormUserVO);
	}
	
	@Override
	public studyInfoVO selectStudyInfoView(String studyCode) throws Exception {
		return studyManagementDAO.selectStudyInfoView(studyCode);
	}
	
	@Override
	public void studyInfoChange(studyInfoVO studyInfoVO) throws Exception{
		studyManagementDAO.studyInfoChange(studyInfoVO);
	}
	
	@Override
	public userInStudyVO selectStudyMemberAdminView(userInStudyVO userInStudyVO) throws Exception {
		return studyManagementDAO.selectStudyMemberAdminView(userInStudyVO);
	}
	
	@Override
	public void studyMemberAdminChange(userInStudyVO userInStudyVO) throws Exception{
		studyManagementDAO.studyMemberAdminChange(userInStudyVO);
	}
	
	@Override
	public void studyMemberMasterChange(userInStudyVO userInStudyVO) throws Exception{
		studyManagementDAO.studyMemberMasterChange(userInStudyVO);
	}
	
	@Override
	public int selectStudyMemberToCnt(String studyCode) throws Exception{
		return studyManagementDAO.selectStudyMemberToCnt(studyCode);
	}
	
	@Override
	public int selectStudyCountToCnt(String studyCode) throws Exception{
		return studyManagementDAO.selectStudyCountToCnt(studyCode);
	}
}
