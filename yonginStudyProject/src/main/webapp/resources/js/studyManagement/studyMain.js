/** 변수 설정(시작) **/
var studyMemberListGrid = new ax5.ui.grid();
var calendarDetailModal = new ax5.ui.modal();
var calendarWriteModal = new ax5.ui.modal();
var studyMemberInfoDetailModal = new ax5.ui.modal();
var extraMask = new ax5.ui.mask();
var cal;
var _pageNo = 0;
var studyCode;		// 현재 페이지 스터디 코드
var studyName; 		// 현재 페이지 스터디 이름
/** 변수 설정(끝) **/

/** 초기화(시작) **/
$(document).ready(function () {
	studyCode = $("#studyCode").val();
	studyName = $("#studyName").val();
	
	document.getElementById("spanStudyMainName").innerText = studyName;
	
	//스터디 멤버 리스트 설정
	studyMemberListGrid.setConfig({   
    	target: $('[data-ax5grid="studyMemberListGrid"]'),
        showLineNumber: false,
        //showRowSelector: true,
        columns: [
        	{key : "userId", label: "ID", align: "center", width:"25%"},
        	{key : "userName", label: "이름", align: "center", width:"25%"},
        	{key : "userAddress",label : "거주지", align : "center",width : "25%"},
        	{key : "studyAuthority",label : "직위", align : "center",width : "25%",
				formatter:function(){
    			    return studyPositionMap[this.value];
    			}
			},
        ],
        header: {
        	align:"center",
        	selector: false
        },
        body: {
                    align: "left",
                    columnHeight: 45,
                    
                    onClick: function () 	{
					},
					onDBLClick: function(){
						selectStudyMemberInfoDetail(this.list[this.dindex]["userCode"],this.list[this.dindex]["userId"]);
					},
					onDataChanged: function(){
					},
                },
        page: {
            navigationItemCount: 9,
            height: 30,
            display: true,
            firstIcon: '<i class="fa fa-step-backward" aria-hidden="true"></i>', 
            prevIcon: '<i class="fa fa-caret-left" aria-hidden="true"></i>',
            nextIcon: '<i class="fa fa-caret-right" aria-hidden="true"></i>',
            lastIcon: '<i class="fa fa-step-forward" aria-hidden="true"></i>',
            onChange: function () {
				_pageNo = this.page.selectPage;
				getStudyMemberList();
                },
            },
        });

		//달력 초기 설정
		cal = new tui.Calendar('#study_calendar', {
		    defaultView: 'month', // monthly view option
		    //useDetailPopup : true,
	    	disableDblClick : true,
	    	disableClick : false,
	    	month : {
	    		daynames : [ '일', '월', '화', '수', '목', '금', '토' ],
	    		startDayOfWeek : 0,
	    	}
		});
		
		cal.on({
			'beforeCreateSchedule' : function(e) {
				// open a creation popup
				if(userAuthority == "10" || userAuthority == "20")
					writeSchedule(e);
				else {
					var guide = e.guide;
					if(e.guide != undefined)
						guide.clearGuideElement();
				}
			},
			'clickSchedule' : function(e) {
				var schedule = e.schedule;
				lastClickSchedule = schedule;
				openCalenderPopup(e);
			}
		});
		
		getStudyMemberList();
		
		/*클릭시 이동 */
		$("#Movetop").click(function(){
			$('html, body').animate({
				scrollTop : 0
			}, 400);
			return false;
		});
		
		var list1 = $("#see_member").offset();
		var list2 = $("#see_cal").offset();
		//클릭시 이동
		$("#movelist1").on("click",function(event){
			$("html body").animate({scrollTop:list1.top},400);
		});
		
		$("#movelist2").on("click",function(event){
			$("html body").animate({scrollTop:list2.top},400);
		});
		
		
		
		/*사이드메뉴 */
		$(".side_fixed_menu_title").click(function(){
			var sidemenu = $(".side_fixed_menu_list");
			
			if(sidemenu.is(":visible")){
				sidemenu.slideUp();
			}else{
				sidemenu.slideDown();
			}
		});
		
		
		var list4 = $("#see_member").offset();
		var list5 = $("#see_cal").offset();
		//클릭시 이동
		$("#side_movelist1").on("click",function(event){
			$("html body").animate({scrollTop:list4.top},400);
		});
		
		$("#side_movelist2").on("click",function(event){
			$("html body").animate({scrollTop:list5.top},400);
		});
		
	
		
		// 관리자 권한 없는 경우 일정 생성 버튼 숨기기 함
		if(!(userAuthority=="10" || userAuthority=="20")){
			$("#createCalendarBtn").hide();
		}
		
		showRange();
		searchMyStudyCalendar();
});
/** 초기화(끝) **/

// 스터디 멤버 상세 보기 팝업 
function selectStudyMemberInfoDetail(userCode, userId){
	var parentData={
			userCode:userCode,
			userId:userId,
			studyCode:$("#studyCode").val(),
			studyName:$("#studyName").val()
	}
	
	studyMemberInfoDetailModal.open({
		width: 600,
		height: 600,
		iframe: {
			method: "post",
			url: "/studyManagement/studyMemberInfoDetail.do",
			param: callBack = parentData
		},
		onStateChanged: function(){
			if (this.state === "open") {
	        	mask.open();
	        }
	        else if (this.state === "close") {
	        	mask.close();
	        }
	    },
	}, function() {
	});
}


/* 스터디 멤버 조회 함수 */
function getStudyMemberList(){
	
	var sendData = {
			page :	_pageNo,
			studyCode : $("#studyCode").val(),
			searchStudyMemberId:$('#searchStudyMemberId').val(),
			searchStudyMemberName:$('#searchStudyMemberName').val(),
			searchStudyMemberAddress:$('#searchStudyMemberAddress').val(),
			searchStudyMemberIsAdmin:$('#searchStudyMemberIsAdmin').val()
	}

	$.ajax({
		type: "POST",
 		url : "/studyManagemet/selectStudyMainMemberList",
		contentType: "application/json; charset=UTF-8",
		data : JSON.stringify(sendData),
		async: false,
		success : function(data, status, xhr) {
			switch(data.result){
			    case COMMON_SUCCESS:
			    	if(data.resultList.length>0){
			    		studyMemberListGrid.setData({
			    			list: data.resultList,
			    		 	page: {
			    		 		currentPage: _pageNo || 0,
			    			 	pageSize: data.dataPerPage,
			    			 	totalElements: data.total,
			    			 	totalPages: data.totalPages
			    		 	}, 
			    		});
			    	}else{
			    		dToast.push("스터디 인원 목록이 없습니다.");
			    		studyMemberListGrid.setData([]);
			    	}
			    	break;    
			    case COMMON_FAIL:
			    	dialog.alert(data.message); 
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log('error = ' + jqXHR.responseText + 'code = ' + errorThrown);
		}
	}); 
}

//EnterKeyEvent
function enterKeyEvent() {
    if (window.event.keyCode == 13) {
         // 엔터키가 눌렸을 때 실행할 내용
    	_pageNo = 0;
    	getStudyMemberList();
    }
}

// 검색 버튼용 조회 함수
function searchStudyMemberList(){
	_pageNo = 0;
	getStudyMemberList();
}

//스케줄 검색
function searchMyStudyCalendar() {
	var today = cal.getDate();
	var month = '' + (today.getMonth()+1);
	if (month.length < 2) month = '0' + month;
	
	var sendData = {
		searchMonthFrom:today.getFullYear() + month + '01',
		searchMonthTo:today.getFullYear() + month + '31',
		studyCode:studyCode
	}

	cal.clear();
	
	searchMyStudyCalendarAjax(sendData);
}

//스케줄 검색 Ajax
function searchMyStudyCalendarAjax(sendData){
	$.ajax({
 		type: "POST",
 		url : "/studyManagement/selectStudyCalendar.json",
 		data : JSON.stringify(sendData),
		contentType: "application/json; charset=UTF-8",
		async: false,
		success : function(data, status, xhr) {
			switch(data.result){
			    case COMMON_SUCCESS:
			    	for(var i=0; i<data.resultList.length; i++) {
						cal.createSchedules([ {
							id : data.resultList[i].studyCode,
							calendarId : data.resultList[i].calendarCode,
							title : "("+data.resultList[i].startHm+") "+data.resultList[i].title,
							body : data.resultList[i].content,
							category : 'time',
							raw:data.resultList[i].type,
							start : data.resultList[i].startDt + 'T' + data.resultList[i].startHm,
							end : data.resultList[i].endDt + 'T' + data.resultList[i].endHm,
							attendees : [data.resultList[i].studyName, data.resultList[i].updtusId],
							borderColor:typeSxnMap[data.resultList[i].type][1],
						}]);
					}
			    	break;    
			    case COMMON_FAIL:
			    	dToast.push(data.message); 
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log('error = ' + jqXHR.responseText + 'code = ' + errorThrown);
		}
	}); 
}

//일정작성 열기
function writeSchedule(e){
	var parentData = {
		studyName : studyName,
		studyCode : studyCode,
		userId : userId,
		calendar : e
	}
	
	calendarWriteModal.open({
		width: 470,
		height: 250,
		iframe: {
			method: "post",
			url: "/studyManagement/calendarWrite.do",
			param: callBack=parentData
		},
		onStateChanged: function(){
			if (this.state === "open") {
	        	extraMask.open();
	        }
	        else if (this.state === "close") {
	        	extraMask.close();
	        	var guide = e.guide;
	        	if(e.guide != undefined)
	        		guide.clearGuideElement();
	        }
	    },
	}, function() {
	});
}


//일정 작성 팝업창 닫고 새로고침
function closeModal(){
	calendarWriteModal.close();
}

//일정 작성 완료 시 일정 조회
function closeModalWithRefresh(){
	calendarWriteModal.close();
	searchMyStudyCalendar();
}

//이전 달로 이동
function prev() {
	cal.prev();
	showRange();
	cal.clear();
	searchMyStudyCalendar();
}

//다음 달로 이동
function next() {
	cal.next();
	showRange();
	cal.clear();
	searchMyStudyCalendar();
}

//현재 달력 범위 보여줌
function showRange(){
	var today = cal.getDate();
	var month = '' + (today.getMonth()+1);
	if (month.length < 2) month = '0' + month;
	document.getElementById("renderRange").innerText = today.getFullYear() + "." + month;
}

//일정 상세 팝업 열기
function openCalenderPopup(e){
	var windowWidth = $(window).width();
	var windowHeight = $(window).height();
	var width = e.event.clientX;
	var height = e.event.clientY;
	
	e.userAuthority = userAuthority;
	console.log(e);
	e.typeName = typeSxnMap[e.schedule.raw][0]
	
	calendarDetailModal.open({
		width: 350,
		height: 290,
		iframe: {
			method: "post",
			url: "/studyManagement/calendarPopup.do",
			param: callBack = e
		},
		onStateChanged: function(){
			if (this.state === "open") {
	        	mask.open();
	        }
	        else if (this.state === "close") {
	        	mask.close();
	        }
	    },
	}, function() {
	});
	
	if(width+300 > windowWidth)
		width = width-300;
	if(height+300 > windowHeight)
		height = height-300;
	calendarDetailModal.align({left:width, top:height});
}

function closeCalenderPopup(){
	calendarDetailModal.close();
}

function closeCalenderPopupWithRefresh(){
	calendarDetailModal.close();
	searchMyStudyCalendar();
}

function updateCalendar(e){
	calendarDetailModal.close();
	writeSchedule(e);
}

//채팅 팝업 열기
function studyChatFunc(){
	var frmPop= document.dataForm;
	var targetName = studyName + "Chat";
 var url = '/studyManagement/studyChatting.do';
  
 window.open("" ,targetName, 
 "toolbar=no, width=700, height=870, directories=no, status=no,    scrollorbars=no, resizable=no"); 
 
 frmPop.method="post";
 frmPop.action = url;
 frmPop.target = targetName; 
 frmPop.studyCode.value = studyCode;
 frmPop.studyName.value = studyName;
 frmPop.submit();   
}

//쪽지보내기 팝업 사이즈 변경
function sendMessageSize(){
	studyMemberInfoDetailModal.css({width:600, height: 750});
	studyMemberInfoDetailModal.align();
}

// 스터디 상세 보기 팝업 닫기
function close(){
	studyMemberInfoDetailModal.close();
}