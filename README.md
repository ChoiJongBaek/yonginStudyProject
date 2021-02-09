# yonginStudyProject

## 소개
- 용인대학교 컴퓨터과학과 졸업작품 발표 프로젝트
  - 4인 1조로 팀을 구성해 Front-End 1명, Back-End 2명, Front-End & Back-End 1명으로 진행
- 원하는 스터디를 생성 or 참여하는 사이트
  - 주제, 지역 선택을 통해 자신이 원하는 스터디 그룹 참여
  
## 사용 기술
### Back-End
- Java EE 8
- Spring 4.3.8 Release
- Apache-Tomcat-9
- Oracle Database 11g Express
- MyBatis 3.4.1
  
### Front-End
- JSP
- CSS
- JavaScript

### Tools
- Eclipse IDE
- Git

### 영상
> [영상 바로가기](https://youtu.be/Xma8l9yGxw4)

## 구현 화면
### 로그인 & 회원가입 & ID/Password 찾기
- ajax를 통한 비동기 방식으로 여러가지 정보를 Server에 전송합니다.
- 유효성 검사를 통한 입력값 적합 여부를 판단합니다.
- 비밀번호 암호화를 통해 DB에 암호화된 Password 값을 저장합니다.
- Email을 통해서 본인 인증 과정을 거칩니다.
- 다음 지도 오픈 API를 이용하여 원하는 지역을 검색할 수 있도록 하였습니다.
  
- 로그인 페이지입니다. <br>
![login](https://user-images.githubusercontent.com/59366717/107307204-98d50480-6ac9-11eb-94b7-3d4663641fcf.jpg)

- 회원가입 페이지입니다. <br>
![sign-up](https://user-images.githubusercontent.com/59366717/107307369-ed787f80-6ac9-11eb-88a8-7ca788d9e176.jpg)

- ID/Password 찾기 페이지입니다. <br>
![findId](https://user-images.githubusercontent.com/59366717/107307634-64157d00-6aca-11eb-83a1-55d5bd616932.jpg)
![findPassword](https://user-images.githubusercontent.com/59366717/107307707-8ad3b380-6aca-11eb-84b2-0f63cecb0a30.jpg)

### 메인 화면
- 자신이 속한 스터디 목록/스터디 공지사항/일정이 확인 가능합니다 <br>
![main1](https://user-images.githubusercontent.com/59366717/107309981-d38d6b80-6ace-11eb-8a1b-e028e0f9d3d6.jpg)
![main2](https://user-images.githubusercontent.com/59366717/107310165-3e3ea700-6acf-11eb-95d8-e6fb5985e298.jpg)
![main3](https://user-images.githubusercontent.com/59366717/107310192-4ac2ff80-6acf-11eb-981f-1c90913eae9a.jpg)

### 전체 스터디 목록 확인
- 전체 스터디 목록이 확인 가능하며 원하는 스터디에 가입 신청을 할 수가 있습니다. <br>
![list](https://user-images.githubusercontent.com/59366717/107315471-21f43780-6ada-11eb-80df-28fa96c80de5.jpg)
![apply](https://user-images.githubusercontent.com/59366717/107315485-27518200-6ada-11eb-994c-27446610018f.jpg)

### 공지사항
- 홈페이지 전체에 대한 공지사항으로 글 작성시에 파일 첨부가 가능합니다.
- 댓글 기능을 통해서 모든 사용자가 소통이 가능하도록 했습니다.
- 부트스트랩을 통해 글 작성시 여러가지 효과를 줄 수 있도록 했습니다. <br>
![noticeWrite](https://user-images.githubusercontent.com/59366717/107310907-b48fd900-6ad0-11eb-9bc4-cc3e098979a6.jpg)
![notice](https://user-images.githubusercontent.com/59366717/107310926-c1acc800-6ad0-11eb-834d-d954beb9ce77.jpg)

### Q&A
- 사용자들이 관리자들한테 질문을 남기는 페이지입니다.
- 댓글은 모두가 달 수 있지만 해당 질문에 대한 답변은 관리자들만 남길 수 있습니다.
![q&a](https://user-images.githubusercontent.com/59366717/107312383-a4c5c400-6ad3-11eb-85fe-5b2f43787137.jpg)
![q&a answer](https://user-images.githubusercontent.com/59366717/107312410-b27b4980-6ad3-11eb-9a55-8114a33ee0e9.jpg)

### 마이페이지
- 회원정보 수정/스터디 관리/신청 현황 등을 관리하는 페이지입니다.
- 소속된 스터디 탈퇴와 관리자일 경우 관리페이지 이동이 가능합니다.
- 유효성 검사를 통해 비밀번호 변경시에 조건을 부여합니다.
- 다음 지도 오픈 API를 이용한 주소 변경을 하도록 했습니다. <br>
![mypage1](https://user-images.githubusercontent.com/59366717/107313145-48fc3a80-6ad5-11eb-947d-8e22620a4243.jpg)
![mypage2](https://user-images.githubusercontent.com/59366717/107313166-587b8380-6ad5-11eb-9ee8-b6dbb947ca52.jpg)
![mypage3](https://user-images.githubusercontent.com/59366717/107313176-5d403780-6ad5-11eb-8eaf-a4c0455880ae.jpg)
![mypage4](https://user-images.githubusercontent.com/59366717/107313191-63361880-6ad5-11eb-97cb-416dedbfcecf.jpg)

### 스터디 페이지
#### 스터디 홈
- 스터디원 검색과 함께 쪽지 보내기, 일정 검색이 가능합니다.
- 스터디원 검색에서는 각각의 검색 조건에 따라 조건에 부합하는 멤버가 검색되도록 검색 기능을 추가했습니다. <br>
![member](https://user-images.githubusercontent.com/59366717/107314133-5e726400-6ad7-11eb-94fb-c3f7632016bf.jpg)
![detail](https://user-images.githubusercontent.com/59366717/107314188-7518bb00-6ad7-11eb-96e7-8ebb734a3229.jpg)
![calendar](https://user-images.githubusercontent.com/59366717/107314237-8eba0280-6ad7-11eb-80b1-4a586c004853.jpg)

#### 스터디 관리자 페이지
- 스터디원 관리/스터디 정보 관리/스터디 신청 관리가 가능한 관리자용 페이지입니다. 
- 일반 사용자, 관리자에 따라 DB 상에서 값을 차별화를 둬서 변경시에 구분이 가능하도록 설정했습니다. <br>
![info](https://user-images.githubusercontent.com/59366717/107314380-d6d92500-6ad7-11eb-9666-48664882947a.jpg)
![register](https://user-images.githubusercontent.com/59366717/107314407-e48eaa80-6ad7-11eb-8cbf-431a14e4d883.jpg)

### 쪽지 페이지
- 사용자들 간에 쪽지를 주고받을 수 있는 쪽지함입니다.
- 발신 쪽지/수신 쪽지 모두 DB에 저장이 되며 삭제시에는 쪽지를 삭제하는게 아닌 상태를 update를 해줌으로써 데이터는 그대로 남겨둡니다. <br>
![write](https://user-images.githubusercontent.com/59366717/107315139-70550680-6ad9-11eb-9f8d-5277081edbbe.jpg)
![send](https://user-images.githubusercontent.com/59366717/107315109-63381780-6ad9-11eb-98dc-8b73730f8412.jpg)
![reception](https://user-images.githubusercontent.com/59366717/107315084-56b3bf00-6ad9-11eb-813b-b38699b4d0c2.jpg)

### 관리자 페이지
- 관리자만 접근이 가능한 페이지로 전체 회원 관리/전체 스터디 관리가 가능한 페이지입니다. <br>
![member](https://user-images.githubusercontent.com/59366717/107315276-b316de80-6ad9-11eb-806a-d08d229ca717.jpg)
![study](https://user-images.githubusercontent.com/59366717/107315289-b7db9280-6ad9-11eb-8352-a7cf027001d5.jpg)
