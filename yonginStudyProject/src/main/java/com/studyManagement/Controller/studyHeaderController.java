package com.studyManagement.Controller; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class studyHeaderController {

	/**
	 * ���͵� ������ Mapping
	 * @throws Exception 
	 */
	@RequestMapping(value = "/studyManagement/studyHeader.do", method = RequestMethod.GET)
	public String studyHeaderForm() throws Exception {
		
		
		return "jsp/studyManagement/studyHeader";
	}
}
 