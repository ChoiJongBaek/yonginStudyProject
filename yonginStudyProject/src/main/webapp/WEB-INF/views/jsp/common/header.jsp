<!------ JSP 설정(시작) ------>
<!-- 한글 설정(시작) -->
<%@ page language="java" contentType="text/HTML;charset=UTF-8" pageEncoding="UTF-8" %>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<!-- 해당 페이지 js 호출 -->
<script type="text/javascript" src="/resources/js/header.js"></script>
</head>
<body> 
  <div class="col-12 point_bg0 header_wrap">
        <div class="col-12 col-center mw-1200 header_con">
                <div class="header_menu_con">
                    <ul class="header_menu">
                        <li><a>HOME</a></li>
                        <li><a>스터디목록</a></li>
                        <li><a>공지사항</a></li>
                    </ul>
                </div>
                
                <div class="header_member_con">
                    <div class="header_user"><span class="user_id">${user.userId}</span>님 환영합니다.</div>
                    <div class="logout">로그아웃</div>
                    
                    <div class="user_box_con">
                       <div class="circle_btn_2"></div>
                       <div class="user_box_tri"></div>
                       <div>
                           <ul class="user_box">
                                 <li><a onclick="openMypageForm();">마이페이지</a></li>
                                <li><a>쪽지함</a></li>
                            </ul>
                       </div>
                    </div>
                </div>
        </div>
      </div>
</body>
</html>