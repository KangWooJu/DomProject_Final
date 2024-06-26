package ac.kr.dankook.ace.dom_t1.Model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionRequestEntity;
import java.util.List;


@Repository
public interface AuctionRequestRepository extends JpaRepository<AuctionRequestEntity,Integer> {
    Optional<AuctionRequestEntity> findByUsername(String username);
    Optional<AuctionRequestEntity> findById(int id);
    
}
