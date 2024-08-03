
<h1>📡단국대학교 컴퓨터공학과 학생들의 웹페이지 프로젝트 - Dom Auction</h1>

: DOM ( Dankook Open Market ) 은 기존의 경매사이트인 KREAM을 기본 모델로 한 아이디어에서 시작하여
  다양한 사용자가 소통을 통해 경매 가격에 대해 서로 소통하고 커뮤니티 형성을 통해 적절한 가격 형성을 도모하는
  오픈 마켓을 지향합니다. 
  


- 📡 백엔드 : Kangwooju,exvcg 
- 📺 프론트엔드 : KwonJinWoo 
---
#### ⓪ 개발 환경 및 역할 분담

< 기술 스택 >
<img src="https://img.shields.io/badge/Spring-#ABF200?style=flat-square&logo=spring&logoColor=white" />
<img src="https://img.shields.io/badge/mysql-#00D8FF?style=flat-square&logo=mysql&logoColor=white" />
<img src="https://img.shields.io/badge/css-#0054FF?style=flat-square&logo=css&logoColor=white" />
<img src="https://img.shields.io/badge/html-#FF5E00?style=flat-square&logo=html&logoColor=white" />
<img src="https://img.shields.io/badge/javascript-#FFE400?style=flat-square&logo=javascript&logoColor=white" />

< 개발진 >
|<img src="https://github.com/KangWooJu.png" width="80">|<img src="https://github.com/exvcg.png" width="80">|<img src="https://github.com/Kw-jinwoo.png" width="80">|
|:---:|:---:|:---:|
|[](https://github.com/ImInnocent)|[](https://github.com/exvcg)|[](https://github.com/Kw-jinwoo)|
|강우주|강민혁|권진우|
|백엔드|백엔드|프론트엔드|

#### ① 프로토타입 
- 리포지토리 : https://github.com/KangWooJu/AJ24_Team1
- 개발 기한 : 3/1 ~ 6/5 

#### ② 리팩토링 기간 : 6/26 ~ 7/26

< 맴버별 리펙토링 이슈 정리 >
강우주 : https://github.com/KangWooJu/DomProject_Final/tree/Kangwooju
강민혁 : https://github.com/KangWooJu/DomProject_Final/tree/master
권진우 : https://github.com/KangWooJu/DomProject_Final/tree/KwonJinWoo

#### ③ 최종 구현 사항

(1) 회원 가입 및 로그인/로그아웃 구현
: Spring Security5의 CSRF 토큰을 통한 회원 가입 구현 및 로그인 / 로그아웃 구현을 통해 
인가된 사용자만 메인 페이지 및 글작성 등 주요 기능을 사용할 수 있도록 설계

(2) 메인 페이지
1. 검색 기능 구현
   : Repository의 JPA 클래스를 통해서 쿼리를 통한 해당하는 카테고리 혹은 키워드를 검색한 뒤 리스트 페이지로 이동하는 기능
2. 카테고리 검색 기능 구현
   : 원하는 카테고리 버튼을 누를 시 해당하는 카테고리로 이동하여 검색 기능 구현과 마찬가지로 해당하는 키워드를 포함하는 데이터를 리스트 페이지로 이동한 뒤에 보여주는 기능

(3) 리스트 페이지
: 원하는 검색 옵션 혹은 모든 데이터를 보여줌으로써 사용자가 원하는 데이터에 접근할 수 있도록 구현

(4) 상세 페이지
1. 사용자가 클릭한 글에 대하여 등록자가 등록한 게시 글에 대해 댓글 및 좋아요 기능 추가
2. 등록자가 자신이 게시한 글에 대하여 삭제 및 수정 기능 추가
3. 해당 경매글에 대하여 입찰을 할 수 있는 기능 추가 : 이때 , 최고 입찰 금액이 상세 페이지에서 보여질 수 있도록 추가함
4. 해당 글이 경매 등록 기간이 지날경우 입찰할 수 없도록 구현

(5) 마이 페이지 
1. 사용자가 자신의 계정 정보를 열람할 수 있도록 구현
2. 사용자가 자신의 계정 정보를 수정할 수 있도록 구현 , 이때 자신의 Username은 변경하지 못하도록 설정


