package com.popit.start.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.popit.start.DAO.testDAO;
import com.popit.start.VO.testVO;

@Service("testService")
public class testServiceImpl implements testService{
	@Autowired
	testDAO testDAO;
	
	// Service Interface�� �Լ��� ���ȭ�� ������, �ش� select �۾��� �� �� DB�� ���� ��ȯ�ϴ� ���Դϴ�.
	public List<testVO> selectlistService(){
		return testDAO.selectlist();
	};
	
	public String aa() {
		String aa = "AA";
		return aa;
	}
}
