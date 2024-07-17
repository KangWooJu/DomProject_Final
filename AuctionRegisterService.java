package ac.kr.dankook.ace.dom_t1.Model.Service;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionRegisterEntity;
import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionRequestEntity;
import ac.kr.dankook.ace.dom_t1.Model.Entity.SiteuserEntity;
import ac.kr.dankook.ace.dom_t1.Model.repository.AuctionRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@RequiredArgsConstructor
@Service
public class AuctionRegisterService {

    @Autowired
    private final AuctionRegisterRepository auctionRegisterRepository;

    public AuctionRegisterEntity getAuctionRegisterEntity(int id) {  // 데이터가 존재하는지 확인하는 메소드 
        Optional<AuctionRegisterEntity> auctionRegisterEntity = this.auctionRegisterRepository.findById(id); // username으로 등록데이터를 조회 , 예외처리로 등록 물품 미확인 메시지 전달
        if (auctionRegisterEntity.isPresent()) {
            return auctionRegisterEntity.get(); // Entity의 get 메소드 실행 -> username 데이터 받아오기 
        } else {
            throw new DataNotFoundException("등록 물품이 확인되지 않습니다!");
        }
    }

    public void AuctionRegisterCreate(String title , String content, SiteuserEntity author,String link,int price,LocalDate et,int c){ // 물품 등록할 때 필요한 데이터 Save 하는 메소드
        AuctionRegisterEntity are = new AuctionRegisterEntity(); // AuctionRegisterEntity 의 새로운 객체 are 생성
        are.setUsername(author.getUsername());
        are.setTitle(title); // 제목 데이터 가져오기
        are.setContent(content); // 내용물 데이터 가져오기 
        are.setCreateDate(LocalDateTime.now()); // 작성 시간 데이터 가져오기 
        are.setAuthor(author); // 작성자 정보 set
        are.setLink(link);//이미지 경로 설정
        are.setPrice(price);//시작가격 설정
        are.setCprice(price);//현재 가격 설정
        are.setHighestprice(price);
        are.setEndtime(et);
        are.setCategory(c);
        are.setHighestuser(author.getUsername());
        this.auctionRegisterRepository.save(are); // 각종 필요한 정보들을 set 한 후에 리포지토리( AuctionRegisterRepository ) 로 넘김 , 그후 CRUD 중 U 시행 
    } //여기에서 사진 관련한 데이터를 추가해야할 경우 넣어주시면 됩니다 

    public Page<AuctionRegisterEntity> getList(int page, String input) { // 페이징을 만드는 모듈 
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate")); // 리스트 넣기 
        Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts)); // 조회할 페이지의 개수 및 최신순으로 정렬 
        Specification<AuctionRegisterEntity> specification = search(input); // specfication 객체에서 Input 을 파라미터로 Search 모듈 실행 
        return this.auctionRegisterRepository.findAll(specification,pageable); // 조회한 내용 Or 조회전의 페이징 내용을 매게로 findAll 모듈 실행 
    }

    public Page<AuctionRegisterEntity> getListMine(String username,int page,int size) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.auctionRegisterRepository.findByAuthorUsername(username,pageable);
    } // 7.14 수정 -> username을 통해서 본인의 개시글을 찾는 리포지토리 명령어를 통해서 페이지 객체 반환 

    private Specification<AuctionRegisterEntity> searchByCategory(String category) {
        return (Root<AuctionRegisterEntity> q, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            query.distinct(true); // 쿼리가 중복되지 않도록 설정 -> 결과 목록에서 중복된 엔티티를 제거 
            return cb.equal(q.get("category"), category); // cb.equal : 두 값이 같은지를 검사
        };
    } // 6.28 추가 : 카테고리가 같은 것만 찾아서 
    // Root : JPA의 Criteria API 클래스 -> 동적으로 쿼리를 생성
    // query : JPA의 Criteria API의 쿼리 객체
    // cb : 쿼리를 빌드하는데 사용되는 객체
    // 새개의 파라미터는 Criteria 클래스를 참고할 필요가있음   

    public Page<AuctionRegisterEntity> getListByCategory(int page, String category) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<AuctionRegisterEntity> specification = searchByCategory(category);
        return this.auctionRegisterRepository.findAll(specification, pageable);
    } // 6.28 추가 : searchByCategory를 이용하여 카테고리 별 리스트 페이징 생성 

    public void modify(AuctionRegisterEntity auctionRegisterEntity,String title,String content,int cprice)
    {
        auctionRegisterEntity.setTitle(title); // 제목 Set 하기 
        auctionRegisterEntity.setContent(content); // 글 내용 Set 하기 
        auctionRegisterEntity.setModifyDate(LocalDateTime.now()); // 수정 시간 Set 하기 
        auctionRegisterEntity.setCprice(cprice);
        this.auctionRegisterRepository.save(auctionRegisterEntity); // 다시 Entity에 Save 하기 
    }
    public void changehighest(AuctionRegisterEntity auctionRegisterEntity,Integer integer,String highestuser)
    {
        auctionRegisterEntity.setHighestprice(integer);
        auctionRegisterEntity.setHighestuser(highestuser);
        this.auctionRegisterRepository.save(auctionRegisterEntity); // 다시 Entity에 Save 하기 
    }

    public void delete(AuctionRegisterEntity auctionRegisterEntity){ // 등록글 삭제 기능 
        this.auctionRegisterRepository.delete(auctionRegisterEntity);
    }

    
    private Specification<AuctionRegisterEntity> search(String input) { // 검색기능 추가 : input을 입력받아 쿼리의 조인문과 where 문을 specification 객체로 return 
        return new Specification<>() { // input : 검색어 
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<AuctionRegisterEntity> q, CriteriaQuery<?> query, CriteriaBuilder cb) { // q: Criteria API의 Root 자료형으로 기준이 되는 AuctionRegisterEntity객체 
                query.distinct(true);  // 중복을 제거 
                Join<AuctionRegisterEntity, SiteuserEntity> u1 = q.join("author", JoinType.LEFT); // 질문 작성자를 검색하기 위해 두개의 entity를 아우터 조인

                return cb.or(cb.like(q.get("title"), "%" + input + "%"), // 제목 
                        cb.like(q.get("content"), "%" + input + "%"),      // 내용 
                        cb.like(u1.get("username"), "%" + input + "%"));   // 답변 작성자 
            }// 검색어 ( input ) 이 존재하는지 Like 키워드를 통해 검색.
        }; 
    }

    public void command(AuctionRegisterEntity auctionRegisterEntity, SiteuserEntity siteuserEntity){ // 7.9 추가 : 좋아요 기능
        auctionRegisterEntity.getCommand().add(siteuserEntity); // entity의 getcommand() 메소드에 좋아요를 누른 사람을 추가 
        this.auctionRegisterRepository.save(auctionRegisterEntity); // 저장 
    }

    
}
