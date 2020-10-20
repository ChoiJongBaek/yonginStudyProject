/** 변수 설정(시작) **/
var studyNoticeDetailBinder = new ax5.ui.binder();	// Binder 설정(데이터를 받아오면 이 Binder set 함으로써 데이터 자동으로 들어감)
var parentData = self.parent.callBack;		// 부모 페이지에서 보낸 데이터 정의
var replyModal = new ax5.ui.modal();
/** 변수 설정(끝) **/

/** 초기화(시작) **/
$(document).ready(function () {
	//summernote editor
	$('#boardDesc').summernote({           
	    height: 250,        
	    codeviewFilter: true,
		codeviewIframeFilter: true,   
		disableDragAndDrop: true,
		toolbar:[]
	});	
	// DIV안에 있는 요소 비활성화
	$("#detailDiv *").prop("disabled", true);
	//섬머노트 비활성화(readonly)
	$('#boardDesc').summernote('disable');
	
	systemNoticeInfoDetailBinder.setModel({}, $(document["noticeInfoDetailForm"]));
	
	selectSystemNoticeInfoDetail(parentData.boardCode);
	
});
/** 초기화(끝) **/

// 부모 페이지에서 받은 systemNoticeCode를 이용해 공지사항 조회
function selectStudyNoticeInfoDetail(boardCode){
	if(boardCode==null || boardCode == ""){
		dialog.alert("정상적인 접근이 아닙니다.");
		return;
	}
	
	$.ajax({
 		type: "POST",
 		url : "/studyManagement/selectStudyNoticeDetail.json",
 		data : boardCode,
		contentType: "application/json; charset=UTF-8",
		async: false,
		success : function(data, status, xhr) {
			switch(data.result){
			    case COMMON_SUCCESS:
			    	studyNoticeInfoDetailBinder.setModel(data.boardInfo);
			    	$('#boardDesc').summernote('code', data.boardInfo.boardDesc);
			    	
			    	var output="";
			    	
			    	for(var i=0;i<data.fileList.length;i++){
			    		output	+= "<a href="+"#"+" id="+data.fileList[i].FILE_CODE+" onclick=" + "fileDownFunc(id); return false;" + ">"+data.fileList[i].ORG_FILE_NAME+"</a> ("+data.fileList[i].FILE_SIZE+"kb)<br>"
			    	}
			    	$("#fileListDiv").html(output);
			    	
			    	getReplyList(boardCode);	//	댓글 조회
			    	
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

// 파일 다운로드 함수
function fileDownFunc(fileCode){
	$("#FILE_CODE").attr("value", fileCode);
	var formObj = $("form[name='readForm']");
	formObj.attr("action", "/fileDown");
	formObj.submit();
}	

// 팝업창 닫기
function closeModal(){
	return self.parent.closeStudyNoticeInfo();		// 부모 페이지의 close함수로 리턴
}

// 수정하기 버튼 함수
function openReviseStudyNotice(){

	location.href = "/studyManagement/reviseStudyNotice.do";
	//window.open("/notice/reviseNotice.do",'공지사항 수정','width=700px ,height=800px ,location=no,status=no,scrollbars=no');
}

