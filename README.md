# DomProject_Final
단국대학교 컴퓨터공학과 학생들의 웹페이지 프로젝트 - Dom Auction 
KwonJinWoo의 작업 Branch

[7/3 개발]
- Category Search를 위한 Method가 추가된 AuctionListController와 AuctionRegisterService를 적용 & dom_main.html을 수정하여 카테고리 클릭기능구현
- Auction_detail.html의 슬라이드 이미지 배치
- AuctionList의 검색창 상단배치 (추후수정)

[7/8 개발]
- AuctionList.html Category Search 구현
- Auction_detail 게시글 좋아요 & 카톡링크 버튼배치
- Auction_detail 댓글 작성자 & 작성시간 배치

[7/17 개발]
- AuctionMyPage Controller & MyPage.html 구현
    (MyPage return중 id값 Null error 발생)
- Auction_detail.html 좋아요 활성화/비활성화 버튼 배치
    (AuctionListController 추천 request method 적용필요)

[7/22 개발]
- MyPage.html 구현 (회원정보/나의판매글)
- Auction_detail.html 입찰종료기능 구현

[7/24 개발]
- 좋아요 기능 구현
    Auction_detail.html 좋아요 버튼 활성화/비활성화 가능
    AuctionRegiserService command() / notcommand() 이용

[7/26 개발]
- 회원정보 수정기능 구현
    modifyUser.html 추가
    AuctionMyPageController userModify(GetMapping/PostMapping) 추가 (usernmae PathVariable로 구현)
