package ac.kr.dankook.ace.dom_t1.Model.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AuctionRegisterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String username; // 고유번호 - 등록자의 아이디

    @Column(length = 200)
    private String title; // 제목 : 글자수 제한 200 

    @Column(columnDefinition = "TEXT") // 텍스트를 열데이터로 넣을 수 있음을 의미하며 글자 수 제한이 없음.
    private String content; // 내용
    @Column
    private double price;
    
    private Integer locationcode;
    
    private Integer category; // 카테고리 ( 각 ) -> 6/26 수정 필요 
    @Column
    private double cprice;
    @Column
    private double highestprice;

    private LocalDateTime createDate; // 작성일자
    private LocalDateTime modifyDate; // 수정날짜
    private LocalDate endtime;//종료날짜
    @OneToMany(mappedBy = "auctionregisterentity", cascade = CascadeType.REMOVE) // CascadeType.REMOVE: 게시물이 삭제되면 Request글들 전부가 한번에 삭제 되도록 한다.
    private List<AuctionRequestEntity> auctionRequestEntityList; // 구매희망(AuctionRequest)를 하나의 리스트에 담고 이를 Entity의 속성에 추가. 이때 , 구매희망에 참조하려면 AuctionRegisterEntity.getAuctionRequestEntityList();
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<AuctionBidEntity> abel; 
    @ManyToOne
    private SiteuserEntity author; // 사용자 한명이 여러개의 글을 작성
    
    @Column
    private String link;//이미지 경로
   
    @Column
    private String highestuser;

    @ManyToMany
    Set<SiteuserEntity> command; // 7/9 추가 : 글 추천자 기능을 위해 Entity에 Set자료형의 GoodVoter 필드 추가 -> 질문 추천 기능 관련 HTML 추가 필수 

} 
