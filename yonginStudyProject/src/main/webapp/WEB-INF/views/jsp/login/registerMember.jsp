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

<!-- 헤더 필요X 생략 -->

<!-- 주소 API javascript 호출(주소 사용하는 곳만 추가) -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>

<!-- 해당 페이지 js 호출 : 순서 3(다른 페이지 js 호출 금지)-->
<script type="text/javascript" src="/resources/js/login/registerMember.js"></script>

<title>회원가입</title>
</head>
<body>

<div class="col-12 register_wrap">
	<div class="col-12 col-center mw-1200 search_memeber_title_wrap">

		<div class="circle_btn type_2" onClick="self.close()"></div> 
		<div class="tc search_memeber_title_con">
			<div class="search_memeber_title content_title"><span>회원 가입</span></div>
		</div> 
	</div>


	
	<form:form method="POST" modelAttribute="userInfoVO" name="registerForm" id="registerForm" action="/registerMember.json">
	<div class="col-12 col-center mw-1200 search_memeber_form_wrap">
			<div class="register_member_form_con">
			
				<div class="user_id search_member_form_con_type_2">
					<div class="title_size type_2"> 아이디</div>
					<div class="register_input_con">
						<form:input path="userId" type="text" name="userId" id="userId" class="textbox_style_1" placeholder="5~20자로 설정해주세요." maxlength="20"/>
						<i class="fa fa-times-circle" aria-hidden="true" id="idYnIcon" style="margin-left:1%"></i>
						<input type="button" value="중복확인" id="checkIdBtn" class="btn_style_1 id_btn" onclick="IdCheckFunc()">
					</div>
				</div>
				
				<div class="user_pw search_member_form_con_type_2">
					<div class="title_size type_2">비밀번호</div>
					<div class="register_input_con">
						<form:input path="userPw" type="password" name="userPw" id="userPw" class="textbox_style_1 type_3" placeholder="10~20자로 설정해주세요."/>
					</div>
				</div>
				
				<div class="user_pw_check search_member_form_con_type_2">
					<div class="title_size type_2">비밀번호 확인</div>
					<div class="register_input_con">
						<form:input path="userPwConfirm" type="password" name="userPwConfirm" id="userPwConfirm" class="textbox_style_1 type_3" placeholder="동일한 비밀번호를 입력해주세요."/>
					</div>
					
				</div>
				
				<div class="user_pw_hint search_member_form_con_type_2">
					<div class="title_size type_2">비밀번호 힌트</div>
					<div class="register_input_con">
						<form:select path="userPwHintCode" name="userPwHintCode" id="userPwHintCode" class="select_style_0" >
					    <c:forEach var="result" items="${pwHint}" varStatus="status">
				          	<option value="<c:out value='${result.codeId}'/>" ><c:out value='${result.codeValue}'/>
				         </c:forEach>
					</form:select>
					</div>
				</div>
				
				<div class="user_pw_ans search_member_form_con_type_2">
					<div class="title_size type_2">비밀번호 힌트 답</div>
					<div class="register_input_con">
						<form:input path="userPwHintAnswer" type="text" name="userPwHintAnswer" id="userPwHintAnswer" class="textbox_style_1"/>
					</div>
				</div>
				
				<div class="user_name search_member_form_con_type_2">
					<div class="title_size type_2">이름</div>
					<div class="register_input_con">
						<form:input path="userName" type="text" name="userName" id="userName" class="textbox_style_1"/>
					</div>
				</div>
				 
				<!-- 생일 선택하는 곳 width 20%안주면 너무 작게 설정됨 -->
				<div class="user_identity_num search_member_form_con_type_2">
					<div class="title_size type_2">생일</div>
					<div class="register_input_con">
						<form:input path="userBirth" type="date" name="userBirth" id="userBirth" class="textbox_style_1 type_4" placeholder="yyyy-mm-dd" max="9999-12-31" maxlength="10" data-ax5formatter="date"/>
					</div>
				</div>
				
				<div class="user_gender search_member_form_con_type_2">
					<div class="title_size type_2">성별</div>
					<div class="register_input_con">
						<div class="radio_btn_con"><form:radiobutton path="userGender" name="userGender" value="m" class="radio_btn"/><span class="radio_gender">남성</span></div>
						<div class="radio_btn_con"><form:radiobutton path="userGender" name="userGender" value="w" checked="checked" class="radio_btn"/><span class="radio_gender">여성</span></div>
					</div>
				</div>
				
				<div class="user_name search_member_form_con_type_2">
					<div class="title_size type_2">이메일</div>
					<div class="register_input_con">
						<form:input path="userEmail" type="text" name="userEmail" id="userEmail" class="textbox_style_1"/>
						<input type="button" value="인증번호 전송" id="initSendMailBtn" class="btn_style_1 type_2" onclick="sendAuthCode()" >
						<input type="button" value="재전송" id="reSendMailBtn"  class="btn_style_1" onclick="sendAuthCode()" >
						<input type="button" value="이메일 변경" id="resetMailBtn"  class="btn_style_1" onclick="resetAuthCode()" >
					</div>
				</div>
				
				<div class="user_id search_member_form_con_type_2" id="emailCodeDiv">
					<div class="title_size type_2">인증번호 </div>
					<div class="register_input_con">
						<input type="text" name="emailCode" id="emailCode" class="textbox_style_1"/>
						<input type="button" id="authCodeBtn" value="인증하기" class="btn_style_1" onclick="checkAuthCode()" >
					</div>
				</div>
				
				<div class="user_phone search_member_form_con_type_2">
					<div class="user_phone_title title_size type_2">휴대폰 번호</div>
					<div class="register_input_con">
						<form:input path="userPhoneNumber" type="text" name="userPhoneNumber" id="userPhoneNumber" class="textbox_style_1 type_3" placeholder="'-'없이 숫자만 입력하세요." maxlength="13"/>
					</div>
				</div>
				
				<div class="user_id search_member_form_con_type_2">
					<div class="title_size type_2">주소</div>
					<div class="register_input_con">
						<form:input path="userAddress" type="text" name="userAddress" id="userAddress" class="textbox_style_1" readonly="true" placeholder="주소 검색을 클릭하세요."/>
						<input type="button" value="주소 검색" class="btn_style_1" onclick="addressPopup()" >
					</div>
					
				</div>
			</div>
	</div>
	
	<div class="col-12 col-center mw-1200 register_memeber_btn_wrap">
		<div class="btn_style_1_con">
			<input type="button" value="확인" class="btn_style_1" onclick="registerMemberFunc()" >
		</div> 
	</div>
</form:form>
	
</div>




  
</body>
</html>
