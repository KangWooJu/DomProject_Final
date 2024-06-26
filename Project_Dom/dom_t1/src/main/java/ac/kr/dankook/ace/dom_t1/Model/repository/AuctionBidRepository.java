package ac.kr.dankook.ace.dom_t1.Model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionBidEntity;
import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionRegisterEntity;
import java.util.List;



public interface AuctionBidRepository extends JpaRepository<AuctionBidEntity , Integer> {

    @Query("SELECT b FROM AuctionBidEntity b WHERE b.auctionRegisterEntity.username = :username ORDER BY b.bidAmount DESC")
    // Query 어노테이션 사용 : AuctionBidEntity를 선택한 후 AuctionRegisterEntity와의 관계를 통해 경매 등록 엔티티에 연결된 입찰을 선택함
    // auctionRegisterEntity에 존재하는 username와 주어진 username이 일치하는 것을 선택 -> 매핑 
    // 이후 관련 username과 매핑된 bidAmount를 기준으로 내림차순 정렬
    AuctionBidEntity findTopByAuctionRegisterEntityOrderByBidAmountDesc(@Param("username") String username);
    // AuctionBidEntity는 반환 유형으로 AuctionBidEntity를 지정하는데 이는 최고 입찰을 나타내는 엔티티임
    // finTop~ 메소드는 Spring Data JPA의 규칙에 따라 작성 -> 최상위 결과 하나만을 가져오도록 함 
    // 쿼리에서 username이라는 이름의 매개변수를 사용하는데 이를 매소드에서 username으로 지정하고 메소드로 전달된 인자가 JPQL 쿼리 내의 해당 매개변수에 매핑
}   
    

