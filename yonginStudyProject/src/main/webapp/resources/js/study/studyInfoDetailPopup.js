/** 변수 설정(시작) **/
var studyInfoDetailBinder = new ax5.ui.binder();	// Binder 설정(데이터를 받아오면 이 Binder set 함으로써 데이터 자동으로 들어감)
var parentData = self.parent.callBack;		// 부모 페이지에서 보낸 데이터 정의
/** 변수 설정(끝) **/

/** 초기화(시작) **/
$(document).ready(function () {
	//summernote editor
	$('#studyDesc').summernote({           
	    height: 150,        
	    codeviewFilter: true,
		codeviewIframeFilter: true,   
		disableDragAndDrop: true,
		toolbar:[]
	});	
	// DIV안에 있는 요소 비활성화
	$("#detailDiv *").prop("disabled", true);
	//섬머노트 비활성화(readonly)
	$('#studyDesc').summernote('disable');
	
	studyInfoDetailBinder.setModel({}, $(document["studyInfoDetailForm"]));
	
	selectStudyInfoDetail(parentData.studyCode);
	
});
/** 초기화(끝) **/

// 부모 페이지에서 받은 studyCode를 이용해 스터디 정보 조회
function selectStudyInfoDetail(studyCode){
	if(studyCode == ""){
		dialog.alert("정상적인 접근이 아닙니다.");
		return;
	}
	
	$.ajax({
 		type: "POST",
 		url : "/study/selectStudyInfoDetail.json",
 		data : studyCode,
		contentType: "application/json; charset=UTF-8",
		async: false,
		success : function(data, status, xhr) {
			switch(data.result){
			    case COMMON_SUCCESS:
			    	studyInfoDetailBinder.setModel(data.studyInfo);
			    	$('#studyDesc').summernote('code', data.studyInfo.studyDesc);
			    	
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
function closeModal(){
	return self.parent.closeStudyInfo();		// 부모 페이지의 close함수로 리턴
}
