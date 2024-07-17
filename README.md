# DomProject_Final
단국대학교 컴퓨터공학과 학생들의 웹페이지 프로젝트 - Dom Auction 




🏷️ < 6월 28일 ( 금 ) 작업 및 수정사항 >

---


• Controller 수정사항 

① AuctionListControlloer.java


1.listByCategory 메소드 생성 : 카테고리별 리스트 페이지 생성




• Service 수정사항 

② AuctionRegisterService.java


1.searchByCategory 메소드 생성 : category와 쿼리의 카테고리를 비교후에 specification 객체를 리턴 ( 주석 참고 )

2.getListByCategory 메소드 생성 : searchByCategory 메소드를 통해서 페이징 구현

---


🏷️ < 7월 9일 ( 화 ) 작업 및 수정사항 >

---

• Entity 수정사항

① AuctionRegisterEntity.java  

1. Set 자료형 - Command 필드 생성 : 글 추천자 기능을 위해 Entity에 Set자료형의 GoodVoter 필드 추가 -> 질문 추천 기능 관련 HTML 추가가 필요함




• Service 수정사항 

① AuctionRegisterService.java 

1. Command 메소드 생성 : 좋아요 기능 구현

---


🏷️ < 7월 11일 ( 목 ) 작업 및 수정사항 >

--- 

• Controller 수정사항 

① AuctionMyPageController.java 

1. @RequiredMapping 어노테이션 추가 : 하부 URL 메핑 메소드에 고정URL 부여 및 필드 멤버 생성 ( 코드 참고 )




• Service 수정사항 

① AuctionRegisterRepository.java 

1. Page 자료형 - FindByAuthorUseranme 메소드 생성 : username을 통해서 해당 사용자가 작성한 글을 찾는 메소드

---


🏷️ < 7월 14일 ( 일 ) 작업 및 수정사항 >

---

• Controller 수정사항 

① AuctionMyPageController.java

1. mypage 메소드 생성 : SecurityAuthenticated 설정 및 메소드 생성 , 사용자의 기본적인 개인 정보를 model에 넘겨주는 메소드 ( 코드 주석 참고 )





• Service 수정사항 

① AuctionRegisterRepository.java 

1. getListMine 메소드 생성 : 기존의 getList의 경우 분류기준이 createDate만 존재 했기 떄문에 username을 통해 해당 유저가 작성한 글을 찾아 페이징 하는 메소드 생성 -> AuctionMyPageController 구현
   ( 주석 참고 ) 

---

