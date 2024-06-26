package ac.kr.dankook.ace.dom_t1.Model.Entity;


import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AuctionRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String username; // 구매 희망자의 id 

    @Column(columnDefinition = "TEXT")
    private String content; // 답변 내용

    @Column
    private Integer price; // 희망 입찰가격

    @Column
    private LocalDateTime createDate; // 생성 시간 

    private LocalDateTime modifyDate;//

    
    @ManyToOne // 하나의 등록글에 대하여 여러개의 구매희망댓글들이 매핑 -> 데이터 베이스 상에서 외래키 형성
    @JoinColumn(name = "auctionregisterentity_title")
    private AuctionRegisterEntity auctionregisterentity; // 옥션등록 엔티티를 참조

    @ManyToOne
    private SiteuserEntity author;
    
    
    @ManyToMany // 다대 다 관계로 설정 
    Set<SiteuserEntity> good; // 좋아요 기능 

    

}
