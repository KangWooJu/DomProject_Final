package ac.kr.dankook.ace.dom_t1.Model.Service;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ac.kr.dankook.ace.dom_t1.Model.Entity.SiteuserEntity;
import ac.kr.dankook.ace.dom_t1.controller.SiteuserRole;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class SiteuserSecurityService implements UserDetailsService 
{ //UserDetailService 인터페이스를 implements : UserDetailService는 스프링 시큐리티가 제공하는 인터페이스의 일종
    private final ac.kr.dankook.ace.dom_t1.Model.repository.SiteuserRepository SiteuserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // UserDetailService 인터페이스가 loadUserByUsername 메서드를 구현하도록 강제함 -> loadUserbyUsername은 사용자명(Username:파라미터 대체 가능)으로 스프링 시큐리티의 사용자(User)객체를 조회

        Optional<SiteuserEntity> _siteUser = this.SiteuserRepository.findByusername(username); 
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        SiteuserEntity siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(SiteuserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(SiteuserRole.USER.getValue()));
        }
        return new User(siteUser.getUsername(), siteUser.getPsw(),authorities); // User 객체의 비밀번호가 사용자로부터 입력받은 비밀번호와 일치하는지 검사
    } // UserDetails 클래스의 User 객체와 Principal 객체가 연결되어있어 Username으로 꼭 맞춰주어야함 ....
}
