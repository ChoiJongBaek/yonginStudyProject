package com.commonCode.DAO;

import java.util.List;
import java.util.Map;

import com.commonCode.VO.commonCodeVO;

public interface commonCodeDAO {
	
	/**
	 * COMMON_CODE_DETAIL�� �ڵ尪���� ��ȸ�Ѵ�.
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<commonCodeVO> selectCommonCodeList(String code) throws Exception;

	public void updateFile(Map<String, Object> map) throws Exception;
	
}
