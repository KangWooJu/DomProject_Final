package ac.kr.dankook.ace.dom_t1.Model.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.*;
import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionRegisterEntity;



@Repository
public interface AuctionRegisterRepository extends JpaRepository<AuctionRegisterEntity,Integer>{
    Optional<AuctionRegisterEntity> findById(int id);
    AuctionRegisterEntity findByTitle (String title); // 등록글 제목으로 데이터 찾기 
    AuctionRegisterEntity findByTitleAndContent(String title, String content); // 제목과 내용으로 찾기 
    List<AuctionRegisterEntity> findByTitleContains (String title); 
    Optional<AuctionRegisterEntity> findByUsername(String username);
    Page<AuctionRegisterEntity> findAll(Pageable pageable); // page 타입의 객체를 생엉하고 타입객체를 리턴하는 findAll 메소드 
    Page<AuctionRegisterEntity> findAll(Specification<AuctionRegisterEntity> specification, Pageable pageable);// Speicification과 Pageable객체를 통해 DB에서 엔티티를 조회한 결과를 페이징 ( 원하는 결과 ) 한 후 반환 
    Page<AuctionRegisterEntity> findByAuthorUsername(String username, Pageable pageable);
    // 7. 11 추가 )  username을 통해서 작성한 글을 찾는 메소드 
    
}
