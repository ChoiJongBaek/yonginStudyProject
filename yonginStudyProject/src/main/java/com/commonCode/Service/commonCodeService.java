package com.commonCode.Service;

import java.util.List;

import com.commonCode.VO.commonCodeVO;

public interface commonCodeService {
	
	/**
	 * COMMON_CODE_DETAIL�� �ڵ尪���� ��ȸ�Ѵ�.
	 * @param code
	 * @return
	 * @throws Exception
	 */
	List<commonCodeVO> selectCommonCodeList(String code) throws Exception;
	
}
