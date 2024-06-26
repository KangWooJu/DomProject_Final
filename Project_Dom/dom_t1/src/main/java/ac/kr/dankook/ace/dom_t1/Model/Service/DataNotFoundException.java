package ac.kr.dankook.ace.dom_t1.Model.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 데이터베이스에서 특정 엔티티 또는 데이터를 찾게될 수 없을 때 방생시키는 예외 클래스 
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found") // -> 예외 발생시 스프링부트가 설정된 HTTP 상태코드와 그에 대한 이유를 포함한 응답을 생성하여 클라이언트에게 반환 
public class DataNotFoundException extends RuntimeException{ // RuntimeException 클래스를 상속받은 DataNotFoundException 클래스 
    private static final long serialVersionUID = 1L;
    public DataNotFoundException(String message) {
        super(message);
    }


}
