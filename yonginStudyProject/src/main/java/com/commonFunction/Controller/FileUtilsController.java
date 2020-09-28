package com.commonFunction.Controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.commonFunction.Service.fileService;
import com.notice.VO.moreNoticeInfoVO;

@Controller
public class FileUtilsController {
	
	@Resource(name="fileService")
	private fileService fileService;
	 
	private static final String filePath = "C:\\Git\\uploadedFileFolder\\"; // ������ ����� ��ġ
	
	public static List<Map<String, Object>> parseInsertFileInfo(moreNoticeInfoVO moreNoticeInfoVO, 
			MultipartHttpServletRequest mpRequest) throws Exception{
		
		/*
			Iterator�� �����͵��� ����ü? ���� �÷������κ��� ������ ���� �� �ִ� �������̽��Դϴ�.
			List�� �迭�� ���������� �������� ������ ����������, Map���� Ŭ�������� ���������� ������ ���� �����ϴ�.
			Iterator�� �̿��Ͽ� Map�� �ִ� �����͵��� while���� �̿��Ͽ� ���������� �����մϴ�.
		*/
		
		Iterator<String> iterator = mpRequest.getFileNames();
		
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtension = null;
		String storedFileName = null;
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null;
		
		String bCode = moreNoticeInfoVO.getSystemNoticeCode();
		
		File file = new File(filePath);
		if(file.exists() == false) {
			file.mkdirs();
		}
		while(iterator.hasNext()) {
			multipartFile = mpRequest.getFile(iterator.next());
			if(multipartFile.isEmpty() == false) {
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = getRandomString() + originalFileExtension;
				
				file = new File(filePath + storedFileName);
				multipartFile.transferTo(file);
				listMap = new HashMap<String, Object>();
				listMap.put("bCode", bCode);
				listMap.put("orgFileName", originalFileName);
				listMap.put("storedFileName", storedFileName);
				listMap.put("fileSize", multipartFile.getSize());
				list.add(listMap);
				
			}
		}
		return list;
	}
	
	public static List<Map<String, Object>> parseUpdateFileInfo(moreNoticeInfoVO moreNoticeInfoVO, String[] files, MultipartHttpServletRequest mpRequest) throws Exception{ 
		Iterator<String> iterator = mpRequest.getFileNames();
		MultipartFile multipartFile = null; 
		String originalFileName = null; 
		String originalFileExtension = null; 
		String storedFileName = null; 
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null; 
		String bCode = moreNoticeInfoVO.getSystemNoticeCode();
		while(iterator.hasNext()){ 
			multipartFile = mpRequest.getFile(iterator.next()); 
			if(multipartFile.isEmpty() == false){ 
				originalFileName = multipartFile.getOriginalFilename(); 
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf(".")); 
				storedFileName = getRandomString() + originalFileExtension; 
				multipartFile.transferTo(new File(filePath + storedFileName)); 
				listMap = new HashMap<String,Object>();
				listMap.put("IS_NEW", "Y");
				listMap.put("bCode", bCode);
				listMap.put("orgFileName", originalFileName);
				listMap.put("storedFileName", storedFileName);
				listMap.put("fileSize", multipartFile.getSize());
				list.add(listMap); 
			} 
		}
		if(files != null){ 
			for(int i = 0; i<files.length; i++) {
					listMap = new HashMap<String,Object>();
                    listMap.put("IS_NEW", "N");
					listMap.put("FILE_CODE", files[i]); 
					list.add(listMap); 
			}
		}
		return list; 
	}
	
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	@RequestMapping(value="/fileDown", method = RequestMethod.POST)
	public void fileDown(@RequestParam Map<String, Object> map, HttpServletResponse response) throws Exception{
		System.out.println("�ڵ� : "+map.get("FILE_CODE"));
		Map<String, Object> resultMap = fileService.selectFileInfo(map);
		String storedFileName = (String) resultMap.get("STORED_FILE_NAME");
		String originalFileName = (String) resultMap.get("ORG_FILE_NAME");
		
		// ������ �����ߴ� ��ġ���� ÷�������� �о� byte[]�������� ��ȯ�Ѵ�.
		byte fileByte[] = org.apache.commons.io.FileUtils.readFileToByteArray(new File(filePath+storedFileName));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition",  "attachment; fileName=\""+URLEncoder.encode(originalFileName, "UTF-8")+"\";");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
	}
}