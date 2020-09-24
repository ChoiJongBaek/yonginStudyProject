package com.notice.DAO;

import java.util.List;

import com.notice.VO.moreNoticeInfoVO;

public interface systemNoticeDAO {

	public List<moreNoticeInfoVO> selectSystemNoticeList(moreNoticeInfoVO moreNoticeInfoVO);
	
	public void insertSystemNotice(moreNoticeInfoVO data) throws Exception;
	
	public void deleteSystemNotice(String messageCode) throws Exception;
	
	public int selectSystemNoticeListToCnt(moreNoticeInfoVO moreNoticeInfoVO);
}