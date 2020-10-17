package com.studyManagement.Controller; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commonCode.Service.commonCodeService;
import com.commonCode.VO.commonCodeVO;
import com.commonFunction.Controller.yonginFunction;
import com.login.VO.userInfoVO;
import com.main.VO.calendarVO;
import com.main.VO.userInStudyVO;
import com.studyManagement.Service.studyMainService;
import com.studyManagement.Validator.calendarValidator;

@Controller
public class studyCalendarController {
   @Resource(name="studyMainService") // �ش� ���񽺰� ���ҽ����� ǥ���մϴ�.
   private studyMainService studyMainService;

   @Resource(name="commonCodeService")
   private commonCodeService commonCodeService;
   
   /**
    * �޷� ���� �ۼ��ϱ�
    * @param session
    * @return
    * @throws Exception
    */
   @RequestMapping(value = "/studyManagement/calendarWrite.do", method = RequestMethod.POST)
   public String calendarWritePopup(HttpSession session, Model model) throws Exception {
      /** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� ������ �� �̵�(����) **/
      userInfoVO user = (userInfoVO) session.getAttribute("user");

      if(user == null) {
         return "jsp/login/login";
      }
      /** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
      
      List<commonCodeVO> codeResult = commonCodeService.selectCommonCodeList("calendarType");
      
      model.addAttribute("calendarType", codeResult);
      
      return "jsp/studyManagement/calendarWrite";
   }
   
   /**
    * �޷� �˾� ����
    * @param session
    * @return
    * @throws Exception
    */
   @RequestMapping(value = "/studyManagement/calendarPopup.do", method = RequestMethod.POST)
   public String calendarDetailPopup(HttpSession session) throws Exception {
      /** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� ������ �� �̵�(����) **/
      userInfoVO user = (userInfoVO) session.getAttribute("user");

      if(user == null) {
         return "jsp/login/login";
      }
      /** ���ǿ� ������ ���������� ��ϵǾ� ���� �ʴٸ� �α��� �������� �̵�(��) **/
      return "jsp/studyManagement/calendarPopup";
   }
   
   /**
    * ���� ����
    * @param calendarVO
    * @param session
    * @param bindingResult
    * @return
    * @throws Exception
    */
   @RequestMapping(value="/studyManagement/saveCalendar.json", method = RequestMethod.POST)
   @ResponseBody
   public Map<String, Object> saveCalendar(@RequestBody calendarVO calendarVO, HttpSession session, BindingResult bindingResult) throws Exception {
         
      HashMap<String, Object> mReturn = new HashMap<String, Object>();
      
      userInfoVO user = (userInfoVO) session.getAttribute("user");
      
      /** ���� üũ(����) **/
      userInStudyVO userinfo = new userInStudyVO();
      
      userinfo.setUserCode(user.getUserCode());
      userinfo.setStudyCode(calendarVO.getStudyCode());
      
      if(userinfo.getUserCode().equals("") || userinfo.getStudyCode().equals("")) {
         mReturn.put("result", "fail");
         mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
         
         return mReturn;
      }
      
      String result = studyMainService.selectStudyUserInfo(userinfo);
      if(!(result.equals("10") || result.equals("20"))) {
         mReturn.put("result", "fail");
         mReturn.put("message", "������ �����ϴ�.");
         
         return mReturn;
      }
      /** ���� üũ(��) **/
      
      /** ������ ����(����) **/
      calendarValidator calendarValidator = new calendarValidator();
      calendarValidator.validate(calendarVO, bindingResult);
      
      // ���� ���� �� ���� �޽����� �Բ� ����
      if (bindingResult.hasErrors()) {
         List<FieldError> errors = bindingResult.getFieldErrors();
         String errorMsg = "";
          for (FieldError error : errors ) {
             errorMsg += error.getDefaultMessage() + "\n";
          }

          mReturn.put("result", "fail");
         mReturn.put("message", errorMsg);
         
         return mReturn;
      }  
      /** ������ ����(��) **/
      
      // ���� ��¥���� '-' ����
      String removeStartDt = yonginFunction.remove(calendarVO.getStartDt(), '-');   //com.commonFunction.Controller�� �ִ� ���� �Լ��� �̿��� ���� ����
      calendarVO.setStartDt(removeStartDt);
      
      // ���� ��¥���� '-' ����
      String removeEndDt = yonginFunction.remove(calendarVO.getEndDt(), '-');   //com.commonFunction.Controller�� �ִ� ���� �Լ��� �̿��� ���� ����
      calendarVO.setEndDt(removeEndDt);
      
      // ���� �ð����� ':' ����
      String removeStartHm = yonginFunction.remove(calendarVO.getStartHm(), ':');   //com.commonFunction.Controller�� �ִ� ���� �Լ��� �̿��� ���� ����
      calendarVO.setStartHm(removeStartHm);
      
      // ���� �ð����� ':' ����
      String removeEndHm = yonginFunction.remove(calendarVO.getEndHm(), ':');   //com.commonFunction.Controller�� �ִ� ���� �Լ��� �̿��� ���� ����
      calendarVO.setEndHm(removeEndHm);
               
      // �ð� ��ȿ�� �˻�
        if(!yonginFunction.validTime(calendarVO.getStartHm()) || !yonginFunction.validTime(calendarVO.getEndHm())) {
           mReturn.put("result", "fail");
            mReturn.put("message", "��ȿ���� ���� ���Դϴ�.");
            
            return mReturn;
        } else if(Integer.parseInt(removeStartDt) >= Integer.parseInt(removeEndDt)) {
           String startDate = removeStartDt+removeStartHm;
           String endDate = removeEndDt+removeEndHm;
           
           if(Long.parseLong(startDate) > Long.parseLong(endDate)) {
               mReturn.put("result", "fail");
                mReturn.put("message", "�ùٸ� �ð� ������ �ƴմϴ�.");
                
                return mReturn;
            }
        }
        
      // �ű� ����
      if(calendarVO.getCalendarCode().equals("NEW")) {
         // ���͵� �ڵ尡 �������� ���� ��� ���� �߻� ��Ŵ
         if(calendarVO.getStudyCode().equals("")) {
            mReturn.put("result", "fail");
            mReturn.put("message", "���͵� ������ �������� �ʽ��ϴ�.");
            
            return mReturn;
         }
         
         calendarVO.setRgstusId(user.getUserCode());
         
         studyMainService.insertCalendar(calendarVO);
         
         mReturn.put("result", "success");
         mReturn.put("message", "���������� ��ϵǾ����ϴ�.");
         
         return mReturn;
      }
      // ����
      else {
         // Ķ���� �ڵ尡 �������� ���� ��� ���� �߻� ��Ŵ
         if(calendarVO.getCalendarCode().equals("")) {
            mReturn.put("result", "fail");
            mReturn.put("message", "Ķ���� ������ �������� �ʽ��ϴ�.");
            
            return mReturn;
         }
         
         calendarVO.setUpdtusId(user.getUserCode());
         studyMainService.updateCalendar(calendarVO);
         
         mReturn.put("result", "success");
         mReturn.put("message", "���������� �����Ǿ����ϴ�.");
         
         return mReturn;
      }
   }
   
   
   /**
    * ���� ����
    * @param calendarVO
    * @param session
    * @param bindingResult
    * @return
    * @throws Exception
    */
   @RequestMapping(value="/studyManagement/deleteCalendar.json", method = RequestMethod.POST)
   @ResponseBody
   public Map<String, Object> deleteCalendar(@RequestBody calendarVO calendarVO, HttpSession session, BindingResult bindingResult) throws Exception {
         
      HashMap<String, Object> mReturn = new HashMap<String, Object>();
      
      userInfoVO user = (userInfoVO) session.getAttribute("user");
      
      calendarVO.setUpdtusId(user.getUserCode());
      
      /** ���� üũ(����) **/
      userInStudyVO userinfo = new userInStudyVO();
      
      userinfo.setUserCode(user.getUserCode());
      userinfo.setStudyCode(calendarVO.getStudyCode());
      
      if(userinfo.getUserCode().equals("") || userinfo.getStudyCode().equals("")) {
         mReturn.put("result", "fail");
         mReturn.put("message", "������ �߻��Ͽ����ϴ�.");
         
         return mReturn;
      }
      
      String result = studyMainService.selectStudyUserInfo(userinfo);
      if(!(result.equals("10") || result.equals("20"))) {
         mReturn.put("result", "fail");
         mReturn.put("message", "������ �����ϴ�.");
         
         return mReturn;
      }
      /** ���� üũ(��) **/
      
      studyMainService.deleteCalendar(calendarVO);
      
      mReturn.put("result", "success");
      mReturn.put("message", "���������� �����Ǿ����ϴ�.");
      
      return mReturn;
   }
   
}
 