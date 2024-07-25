package ac.kr.dankook.ace.dom_t1.Model.Service;

import java.util.*;
import org.springframework.stereotype.Service;

import ac.kr.dankook.ace.dom_t1.Model.Entity.SiteuserEntity;
import ac.kr.dankook.ace.dom_t1.Model.repository.SiteuserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Service
public class SiteuserService {

    
    private final SiteuserRepository siteuserRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteuserEntity create(String username, String email, String psw, String hint, String nickname) { //  SiteuserRepository를 이용하여 회원 데이터를 생성하는 메서드
        SiteuserEntity user = new SiteuserEntity(); 
        user.setUsername(username); // Entity에 id 넣기
        user.setHint(hint); // Entity에 hint 넣기
        user.setNickname(nickname); // Entity에 nickname 넣기
        user.setEmail(email); // Entity에 email 넣기
        user.setPsw(passwordEncoder.encode(psw)); // BCryptPasswordEncoder 객체를 직접 사용하지 않고 빈으로 등록한 PassWordEncoder 객체를 주입받아 사용할 수 있도록 수정
        this.siteuserRepository.save(user);
        return user;
    }

    // 7.22 수정사항 : 사용자의 정보를 변경하는 메소드 생성 
    public void modify(String username,String email,String psw,String hint,SiteuserEntity user){
        user.setUsername(username);
        user.setHint(hint);
        user.setEmail(email);
        user.setPsw(psw);
        this.siteuserRepository.save(user);
    }

    // 7.22 수정사항 : 기존의 getUser 메소드의 파라미터 변경 -> 이부분 꼭 참고
    public SiteuserEntity getUser(String username) { // 사용자 찾아오기 모듈
        Optional<SiteuserEntity> siteUser = this.siteuserRepository.findByusername(username); // 사용자의 id를 통해서 데이터 찾기 -> 없을 경우 예외처리 
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("사용자를 찾을 수 없습니다!"); // 예외처리 : 테이블에 id가 없을경우 메시지 보내기 및 DataNotFoundException 실행
        }
    }

    
}
