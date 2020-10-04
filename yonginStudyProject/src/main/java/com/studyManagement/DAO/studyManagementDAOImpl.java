package com.studyManagement.DAO;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.studyManagement.VO.studyManagementInfoVO;

@Repository
public class studyManagementDAOImpl implements studyManagementDAO{
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public List<studyManagementInfoVO> selectStudyMemberList(studyManagementInfoVO studyManagementInfoVO){
		return sqlSession.selectList("studyManagementMapper.selectStudyMemberList", studyManagementInfoVO);
	}
	
	@Override
	public int selectStudyMemeberListToCnt(studyManagementInfoVO studyManagementInfoVO)	{
		return sqlSession.selectOne("studyManagementMapper.selectStudyMemeberListToCnt", studyManagementInfoVO);
	}
}