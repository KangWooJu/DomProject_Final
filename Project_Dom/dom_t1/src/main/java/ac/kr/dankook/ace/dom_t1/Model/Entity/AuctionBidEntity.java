package ac.kr.dankook.ace.dom_t1.Model.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuctionBidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 경매 등록자 ( 5.28 수정 )

    @Column
    private String username;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AuctionRegisterEntity auctionRegisterEntity; // 경매 등록글에 대하여 여러 입찰 금액 중 최고 입찰금액을 추적 ( 5.28 수정 )

    @ManyToOne
    private SiteuserEntity bidder; // 경매 글에 대한 입찰자 ( 5.28 수정 )

    private Integer bidAmount; // 경매 입찰 금액 ( 5.28 수정 )

    private LocalDateTime bidDate; // 경매 입찰 일자 ( 5.28 수정 )
}
