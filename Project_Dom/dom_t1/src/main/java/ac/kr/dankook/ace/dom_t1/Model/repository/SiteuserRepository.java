package ac.kr.dankook.ace.dom_t1.Model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ac.kr.dankook.ace.dom_t1.Model.Entity.SiteuserEntity;
import java.util.*;

@Repository
public interface SiteuserRepository extends JpaRepository<SiteuserEntity,String> {
    Optional<SiteuserEntity> findByusername(String username); // SiteuserEntity에서 id 를 찾도록 함

}