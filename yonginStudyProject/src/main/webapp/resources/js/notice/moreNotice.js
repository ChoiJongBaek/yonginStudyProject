var noticeListPlusGrid = new ax5.ui.grid();

$(document).ready(function () {
	//공지사항 목록 더보기 리스트 설정
	noticeListPlusGrid.setConfig({   
    	target: $('[data-ax5grid="noticeListPlusGrid"]'),
        showLineNumber: false,
        showRowSelector: true,
        columns: [ 
            {key : "studyName", label: "스터디 이름", align: "center", width:"45%", sortable: true},
        	{key : "noticeTitle", label: "제목", align: "center", width:"45%"},
        	{key : "noticeCnt", label: "조회 수", align: "center", width:"10%"},
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
					onDataChanged: function(){
						
					},
                },
        
        page: {
            navigationItemCount: 9,
            height: 30,
            display: true,
            firstIcon: '|<', 
            prevIcon: '<',
            nextIcon: '>',
            lastIcon: '>|',
            display: false,
            onChange: function () {
                },
            },
        });
});

/*function openstudyApplyPopForm(){ 
	window.open("/studyApplyPop.do",'회원가입','width=700px ,height=900px ,location=no,status=no,scrollbars=no');
}*/
