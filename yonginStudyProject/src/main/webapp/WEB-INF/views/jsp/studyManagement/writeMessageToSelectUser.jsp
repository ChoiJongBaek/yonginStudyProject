<!------ JSP 설정(시작) ------>
<!-- 한글 설정(시작) -->
<%@ page language="java" contentType="text/HTML;charset=UTF-8" pageEncoding="UTF-8" %>
<%
request.setCharacterEncoding("UTF-8");
%>
<!-- 한글 설정(끝) -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!------ JSP 설정(끝) ------>

<!DOCTYPE html>
<html>
<head>
<title></title>

<!---- 순서 다르면 오류 ---->
<!-- 자원 불러오기(공통) : 순서  1(필수)-->
<jsp:include page="../common/resources.jsp"></jsp:include>

<!-- 해당 페이지 js 호출 : 순서 3(다른 페이지 js 호출 금지)-->
<script type="text/javascript" src="/resources/js/studyManagement/writeMessageToSelectUser.js"></script>
	
<script>
</script>
</head>
<body>


	<div class="col-12 reply_message_pop_wrap">
		<div class="col-12 col-center mw-1200 reply_message_pop_con">
			<div class="circle_btn" onClick="closeModal()"></div> 
			<div class="tc reply_message_pop_title"><span>쪽지 보내기</span></div>
		</div>
	</div>
	
	
	<div class="col-12 send_message_content_con">
		<div class="study_manage_send_message_name_wrap">
		
			<div class="send_message_form_con type_2">
     	       	 <div class="name_title_size_1">제목</div>
     	      	  <input type="text" id="messageTitle" name="messageTitle" class="name_textbox_style_0" maxlength="30">
     		</div>
			<div class="send_message_form_con type_2">
				 <div class="title_size type_1">받는 사람 ID</div>
    	         <input type="text" id="userCodeTo" name ="userCodeTo" class="textbox_style_1" readonly="true">
			</div>
     	</div>			
	</div>
	
	<div class="col-12 col-center mw-1200 send_message_summernote_pop_con">
			<div class="col-12 reply_summer_note_pop ">
     		   	<textarea name="messageDesc" id="messageDesc" class="reply_summer_note_textarea"></textarea>
     		</div>
	</div>
	
	 <div class="col-12 reply_btn_style_1_con">
	      	<input type="button" value="보내기" class="btn_style_1" onclick="sendMessage()" >
	      	<input type="button" value="닫기" class="btn_style_1" onclick="closeModal()" >
  	 </div> 

</body>
</html>