/** 변수 설정(시작) **/
var studyNoticeInfoDetailBinder = new ax5.ui.binder();	// Binder 설정(데이터를 받아오면 이 Binder set 함으로써 데이터 자동으로 들어감)
var parentData = self.parent.callBack;		// 부모 페이지에서 보낸 데이터 정의
/** 변수 설정(끝) **/

/** 초기화(시작) **/
$(document).ready(function () {
	//summernote editor
	$('#studyNoticeDesc').summernote({           
	    height: 250,        
	    codeviewFilter: true,
		codeviewIframeFilter: true,   
		disableDragAndDrop: true,
		toolbar:[]
	});	
	// DIV안에 있는 요소 비활성화
	$("#detailDiv *").prop("disabled", true);
	//섬머노트 비활성화(readonly)
	$('#studyNoticeDesc').summernote('disable');
	
	studyNoticeInfoDetailBinder.setModel({}, $(document["studyNoticeInfoDetailForm"]));
	
	selectStudyNoticeInfoDetail(parentData.studyNoticeCode);
	
});
/** 초기화(끝) **/

// 부모 페이지에서 받은 systemNoticeCode를 이용해 공지사항 조회
function selectStudyNoticeInfoDetail(studyNoticeCode){
	if(studyNoticeCode == ""){
		dialog.alert("정상적인 접근이 아닙니다.");
		return;
	}
	
	$.ajax({
 		type: "POST",
 		url : "/main/selectStudyNoticeInfoDetail.json",
 		data : studyNoticeCode,
		contentType: "application/json; charset=UTF-8",
		async: false,
		success : function(data, status, xhr) {
			switch(data.result){
			    case COMMON_SUCCESS:
			    	studyNoticeInfoDetailBinder.setModel(data.studyNoticeInfo);
			    	$('#studyNoticeDesc').summernote('code', data.studyNoticeInfo.studyNoticeDesc);
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

// 팝업창 닫기
function closeModalWithRefresh(){
	return self.parent.writeModalCloseWithRefresh();		// 부모 페이지의 close함수로 리턴
}