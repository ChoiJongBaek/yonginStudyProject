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
<jsp:include page="../studyManagement/studyHeader.jsp"></jsp:include>

<!-- 해당 페이지 js 호출 : 순서 3(다른 페이지 js 호출 금지)-->
<script type="text/javascript" src="/resources/js/studyManagement/studyMain.js"></script>


<meta charset="UTF-8">
<title>Insert title here</title> 
</head>
<body>
   
   <div class="col-12 study_main_wrap">
   		<div class="study_main_content_img"></div>
		<div class="study_main_content_dim"></div>
		
		<div class="study_main_title_con">
			<div class="tc study_main_title"><span class="study_main_name">스터디이름</span>에 오신걸 환영합니다 !</div>
			<div class="tc study_main_sub_title">스터디원과 힘을 모아 공동된 목표를 향해 나아가세요</div>
		</div>
		
		<div class="study_main_content_con">
			<div class="study_main_content">
				<div class="tc study_main_content_img_con"><img src="../resources/img/mymake.png" class="info_img"></div>
				<div class="tc study_main_content_title">스터디원 보기</div>
				<div class="tc study_main_content_sub_title">나와 함께는 스터디원을 확인하세요</div>
			</div>
			
			<div class="study_main_content">
				<div class="tc study_main_content_img_con"><img src="../resources/img/cal.png" class="cal_img"></div>
				<div class="tc study_main_content_title">스터디 일정</div>
				<div class="tc study_main_content_sub_title">스터디의 일정을 확인하세요</div>
			</div>
			
			<div class="study_main_content">
				<div class="tc study_main_content_img_con"><img src="../resources/img/chatting.png" class="chatting_img"></div>
				<div class="tc study_main_content_title">스터디 채팅</div>
				<div class="tc study_main_content_sub_title">스터디 채팅을 통해 소통하세요</div>
			</div>
		</div>
   </div>
   
   <div class="col-12 study_memeber_wrap">
   		<div class="col-12 col-center mw-1200 study_memeber_con">
   			<div class="study_memeber_title_con">
				<div class="study_memeber_title"><span>스터디원 보기</span></div>
				<div class="question_mark_con">
					<div class="tc question_mark"><span>?</span></div>
					<div class="qestion_desc_box_con">
						<div class="question_tri"></div>
						<div class="question_desc">현재 함께 스터디를 하는 동료들을 볼 수 있습니다.</div>
					</div>
				</div>
			</div>
   		</div>
   		
   		<div class="col-12 col-center mw-1200 study_member_grid_con"style="width: 100%;" >  
	  		<div data-ax5grid="studyMemberListGrid" data-ax5grid-config="{}" class="" style="height:300px; padding-top:10px; padding-right:10px"></div>  
		</div>
   		
   </div>
   
   <div class="col-12 study_plan_wrap">
   		<div class="col-12 col-center mw-1200 study_plan_con">
   			<div class="study_plan_title_con">
				<div class="study_plan_title"><span>스터디원 보기</span></div>
				<div class="question_mark_con">
					<div class="tc question_mark"><span>?</span></div>
					<div class="qestion_desc_box_con">
						<div class="question_tri"></div>
						<div class="question_desc">현재 함께 스터디를 하는 동료들을 볼 수 있습니다.</div>
					</div>
				</div>
			</div>
			
			<div id="menu" class="calender_menu">
				<span id="menu-navi">
					  <button type="button" class="prev_btn" data-action="move-prev" onclick="prev();">
					  	<i class="calendar-icon ic-arrow-line-left" data-action="move-prev"></i>
					  </button>
					  <span id="renderRange" class="render-range" style="font-size:2.5rem;"></span>
					  <button type="button" class="next_btn" data-action="move-next" onclick="next();">
					    <i class="calendar-icon ic-arrow-line-right" data-action="move-next"></i>
					  </button>
				</span>
				<span id="renderRange" class="render-range"></span>
			</div>
   			<div id="study_calendar"></div>	
			
			
			
   		</div>   		  		
   </div>
	
	<%-- 
   <div class="col-12 mypage_content_wrap">
   		<div class="side_fixed_menu_con">
			<div class="side_fixed_menu_list_con">
				<ul class="side_fixed_menu_list">
					<li><a><img src="/resources/img/arrow.png" class="side_arrow_img" id="Movetop"></a></li>
					<li><a><img src="/resources/img/info.png" class="side_myinfo_img" id="side_movelist1"></a></li>
					<li><a><img src="/resources/img/mystudy.png" class="side_mystudy_img" id="side_movelist2"></a></li>
					<li><a><img src="/resources/img/mymake.png" class="side_mymake_img" id="side_movelist3"></a></li>
					<li><a><img src="/resources/img/myapply.png" class="side_myapply_img" id="side_movelist4"></a></li>
				</ul>
			</div>
			<div class="tc side_fixed_menu_title">빠른 이동</div>			
		</div> 
		<div class="mypage_content_img"></div>
		<div class="mypage_content_dim"></div>
		
		<div class="col-6 mymage_left_content_con">
			<div class="tc mypage_left_title">마이페이지</div>
			<div class="tc mypage_left_subtitle">한눈에 알아보는 나의 정보</div>
			<div class="tc mypage_left_subtitle2">우측 버튼을 클릭해서 원하는 정보를 확인하세요</div>
		</div>
		<div class="col-6 mymage_right_content_con">
			<div class="right_content_con">
				<div class="right_content" id="listMove1">
					<div class="right_content_img_con"><img src="../resources/img/info.png" class="info_img"></div>
					<div class="right_content_title">개인 정보 수정</div>
					<div class="right_content_subtitle">보안을 위해 주기적으로 바꿔요 </div>
				</div>
				<div class="right_content" id="listMove2">
					<div class="right_content_img_con"><img src="../resources/img/mystudy.png" class="mystudy_img"></div>
					<div class="right_content_title">참여한 스터디</div>
					<div class="right_content_subtitle">내가 참여한 스터디를 보여줘요 </div>
				</div>
			</div>
			<div class="right_content_con">
				<div class="right_content" id="listMove3">
					<div class="right_content_img_con"><img src="../resources/img/mymake.png" class="mymake_img"></div>
					<div class="right_content_title">만든 스터디</div>
					<div class="right_content_subtitle">내가 만든 스터디를 보여줘요 </div>
				</div>
				<div class="right_content" id="listMove4">
					<div class="right_content_img_con"><img src="../resources/img/myapply.png" class="myapply_img"></div>
					<div class="right_content_title">신청 현황</div>
					<div class="right_content_subtitle">내가 신청한 스터디를 보여줘요 </div>
				</div>
			</div>
		</div>
	</div>	
	
	<div class="col-12 mypage_myinfo_wrap" id="list1">
		<div class="col-12 col-center mw-1200 mypage_myinfo_con">
			<div class="mypage_myinfo_title_con">
				<div class="mypage_myinfo_title"><span>개인 정보 수정</span></div>
				<div class="question_mark_con">
					<div class="tc question_mark"><span>?</span></div>
					<div class="qestion_desc_box_con">
						<div class="question_tri"></div>
						<div class="question_desc">개인 정보 수정은 비밀번호와 이메일, 주소 등 개인정보를 쉽게 변경 할 수 있습니다.</div>
					</div>
				</div>
			</div>
			
			<form:form method="POST" modelAttribute="userInfoVO" name="changeInfoForm" id="changeInfoForm">
                    <div class="col-6 modify_line_left_con">
               			<div class="modify_name modify_con first_line_right">
                     		<div class="title_size type_5">이름</div>
                    		<form:input path ="userName" type="text" id="userName" class="textbox_style_1" value='${currentUser.userName }' readonly="true"/>
                     	</div>
                      	<div class="modify_id modify_con">
                   	 		<div class="title_size type_5">아이디</div>
                   	 		<form:input path ="userId" type="text" id="userId" class="textbox_style_1" value='${currentUser.userId }' readonly="true"/>
                   	 		<input class="btn_style_1 type_2" type="button" value="비밀번호 변경" onclick="changePw()">
               			</div>
                		 <div class="modify_phonenumber modify_con">
					 		<div class="title_size type_5">이메일</div>
					 		<div class="register_input_con">
								<form:input path="userEmail" type="text" name="userEmail" id="userEmail" class="textbox_style_1" value='${currentUser.userEmail }'/>
								<input type="button" value="인증번호 전송" id="initSendMailBtn" class="btn_style_1 type_2" onclick="sendAuthCode()" >
								<input type="button" value="재전송" id="reSendMailBtn"  class="btn_style_1" onclick="sendAuthCode()" >
								<input type="button" value="이메일 변경" id="resetMailBtn"  class="btn_style_1" onclick="resetAuthCode()" >
					 		</div>
						</div>	
               	   </div>    	                        	               	
               	   <div class="col-6 modify_line_right_con">
               	  		<div class="modify_phonenumber modify_con first_line_left">
                     		<div class="title_size type_5">전화번호</div>
                     		<form:input path="userPhoneNumber" type="text" id="userPhoneNumber" class="textbox_style_1" value='${currentUser.userPhoneNumber }' maxlength="13"/>
                    	</div>
                    	<div class="modify_phonenumber modify_con">
                     		<div class="title_size type_5">주소</div>
                     		<form:input path="userAddress" type="text" name="userAddress" id="userAddress" class="textbox_style_1" readonly="true" placeholder="주소 검색을 클릭하세요." value='${currentUser.userAddress }' />
					 		<input type="button" value="주소 검색" class="btn_style_1 type_2" onclick="addressPopup()" >
               			</div>
                       
            	    </div>
            	    
            	  <div class="tc col-12 modify_btn_con">
                  	<input class="btn_style_1" type="button" value="수정하기" onclick="changeUserInfo()">
           	  	  </div>               	                            	                			
               </form:form> 
		</div>
	</div>
	
	<div class="col-12 mypage_mystudy_wrap" id="list2">
		<div class="col-12 col-center mw-1200 mypage_mystudy_con">
			<div class="mypage_mystudy_title_con">
				<div class="mypage_mystudy_title"><span>내가 참여한 스터디</span></div>
				<div class="question_mark_con">
					<div class="tc question_mark"><span>?</span></div>
					<div class="qestion_desc_box_con">
						<div class="question_tri type_2"></div>
						<div class="question_desc type_2">현재 본인이 참가하고 있는 스터디의 목록입니다. 목록클릭시 상세보기가 가능합니다.</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-12 col-center mw-1200 my_participate_study"style="width: 100%;" >  
	  			<div data-ax5grid="myParcitipateListGrid" data-ax5grid-config="{}" class="color_grid" style="height:300px; padding-top:10px; padding-right:10px"></div>  
		</div>
	</div>
	
	<div class="col-12 mypage_mymake_wrap" id="list3">
		<div class="col-12 col-center mw-1200 mypage_mymake_con">
			<div class="mypage_mymake_title_con">
				<div class="mypage_mymake_title"><span>내가 만든 스터디</span></div>
				<div class="question_mark_con">
					<div class="tc question_mark"><span>?</span></div>
					<div class="qestion_desc_box_con">
						<div class="question_tri"></div>
						<div class="question_desc">현재 본인이 참가하고 있는 스터디의 목록입니다. 목록클릭시 상세보기가 가능합니다.</div>
					</div>
				</div>
			</div>
		</div>
		
		 <div class="col-12 col-center mw-1200 my_make_study"style="width: 100%;" >  
	  			<div data-ax5grid="myMakeListGrid" data-ax5grid-config="{}" style="height:300px; padding-top:10px; padding-right:10px"></div>  
		</div>
	</div>
	
	<div class="col-12 mypage_myapply_wrap" id="list4">
		<div class="col-12 col-center mw-1200 mypage_myapply_con">
			<div class="mypage_myapply_title_con">
				<div class="mypage_myapply_title"><span>나의 스터디 현황</span></div>
				<div class="question_mark_con">
					<div class="tc question_mark"><span>?</span></div>
					<div class="qestion_desc_box_con">
						<div class="question_tri type_2"></div>
						<div class="question_desc type_2">현재 본인이 신청한 스터디 목록입니다. 클릭시 신청 여부와 상세정보를 알수 있습니다.</div>
					</div>
				</div>
			</div>
		</div>
		
		 	<div class="col-12 col-center mw-1200 my_make_study"style="width: 100%;" >  
	  				<div data-ax5grid="myApplicationFormGrid" data-ax5grid-config="{}" class="color_grid" style="height:300px; padding-top:10px; padding-right:10px"></div>  
			</div>
	</div> --%>
	
</body>
</html> 