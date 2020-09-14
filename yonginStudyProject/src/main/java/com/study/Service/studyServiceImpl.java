package com.study.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.DAO.studyDAO;
import com.main.VO.studyInfoVO;

@Service("studyService")
public class studyServiceImpl implements studyService{
	@Autowired
	studyDAO studyDAO;
	
	@Override
	public int selectSameStudyName(String studyName) throws Exception{
		return studyDAO.selectSameStudyName(studyName);
	}
	
	@Override
	public void insertStudy(studyInfoVO data) throws Exception{ 
		studyDAO.insertStudy(data);
	}
	
	@Override
	public List<studyInfoVO> selectStudyList(){
		return studyDAO.selectStudyList();
	}
}
 