<!------ JSP 설정(시작) ------>
<%@ page language="java" contentType="text/HTML;charset=UTF-8" pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!------ JSP 설정(끝) ------>
    
<!DOCTYPE html>
<html>
<head>
<!---- 순서 다르면 오류 ---->
<!-- 자원 불러오기(공통) : 순서  1(필수)-->
<jsp:include page="../common/resources.jsp"></jsp:include>

<!-- 헤더 불러오기 : 순서 2(헤더 필요없는 곳은 주석처리) -->
<%-- <jsp:include page="../common/header.jsp"></jsp:include> --%>

<!-- 주소 API javascript 호출(주소 사용하는 곳만 추가) -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<!-- 해당 페이지 js 호출 : 순서 3(다른 페이지 js 호출 금지)-->
<script type="text/javascript" src="/resources/js/notice/reviseNotice.js"></script>

<script>
var rgstusIdCode = '${user.userCode}';		// 세션에 있는 현재 접속한 유저의 코드 값을 가져옴

</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="col-12 col-center mw-1200 study_detail_pop_wrap">
		 <div class="circle_btn" onClick="closeModal()"></div> 
		 <div class="tc content_title"><span>공지사항 수정</span></div>
		 
		 <form:form method="POST" modelAttribute="moreNoticeInfoVO" name="reviseNoticeForm" id="reviseNoticeForm" >
		 <div class="study_detail_pop_wrap_con" id="detailDiv">
			
				<form:input path="systemNoticeCode" type="hidden" name="systemNoticeCode" id="systemNoticeCode" data-ax-path="systemNoticeCode" class="textbox_style_1"/>
				
				<div class="study_detail_con detail_name">
					<div class="title_size type_2">제목</div>
					<div class="study_detail_input_con">
						<form:input path="systemNoticeTitle" type="text" name="systemNoticeTitle" id="systemNoticeTitle" data-ax-path="systemNoticeTitle" class="textbox_style_1"/>
					</div>
				</div>				
				
				<div class="detail_desc">
					<div class="title_size type_2">공지사항 내용</div>
					<div class="study_detail_input_con">
						<form:textarea path="systemNoticeDesc" id="systemNoticeDesc" name="systemNoticeDesc" data-ax-path="systemNoticeDesc" style=""/>
					</div> 
				</div>
				
			</div>
				<div class="btn_style_1_con">
					<input type="button" value="닫기" class="btn_style_1" onclick="closeModal()" >
					<input type="button" value="수정완료" class="btn_style_1" onclick="reviseSystemNoticeFunc()" >
				</div> 
		 </form:form>
		 
		 
	</div>
</body>
</html>