<!------ JSP 설정(시작) ------>
<%@ page language="java" contentType="text/HTML;charset=UTF-8" pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!------ JSP 설정(끝) ------>
    
<!DOCTYPE html>
<html>
<head>
<!---- 순서 다르면 오류 ---->
<!-- 자원 불러오기(공통) : 순서  1(필수)-->
<jsp:include page="../common/resources.jsp"></jsp:include>

<!-- 헤더 불러오기 : 순서 2(헤더 필요없는 곳은 주석처리) -->
<jsp:include page="../message/messageSidemenu.jsp"></jsp:include>

<!-- 해당 페이지 js 호출 : 순서 3(다른 페이지 js 호출 금지)-->
<script type="text/javascript" src="/resources/js/message/messageList.js"></script>


<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<div class="col-12 get_message_wrap" id="move1">
		<div class="col-12 col-center mw-1200 get_message_con">
			
			<div class="get_message_title_con">
				<div class="get_message_title"><span>받은 쪽지함</span></div>
				<div class="question_mark_con">
					<div class="tc question_mark"><span>?</span></div>
					<div class="qestion_desc_box_con">
						<div class="question_tri"></div>
						<div class="question_desc">개인 정보 수정은 비밀번호와 이메일, 주소 등 개인정보를 쉽게 변경 할 수 있습니다.</div>
					</div>
				</div>
			</div>
			
			<div class="get_message_search_con">
				<div class="get_message_search_box send_person">
					<div class="get_message_box_title">보낸사람 ID</div>
					<input type="text" id="userCodeFrom" name ="userCodeFrom" class="textbox_style_1" onkeyup="enterKeyEvent();">
				</div>
				<div class="get_message_search_box send_title">
					<div class="get_message_box_title">쪽지 제목</div>
					<input type="text" id="userCodeFrom" name ="userCodeFrom" class="textbox_style_1" onkeyup="enterKeyEvent();">
				</div>
				<div class="get_message_search_box get_search_btn">
					  <input type="button" value="검색" class="btn_style_1" onclick="searchMessageList()" >
				</div>
			</div>
			
			<div class="get_message_grid_con" style="width: 100%;" >  
	  			<div  data-ax5grid="messageListGrid" data-ax5grid-config="{}" style="height:500px; padding-top:10px; padding-right:10px"></div>  
			</div> 
			
			<div class="message_btn">
				<input type="button" value="쪽지 삭제하기" class="btn_style_1" onclick="deleteMessage();">
			</div>
			
			
		</div>
	</div>
	
         
	 
</body>
</html> 