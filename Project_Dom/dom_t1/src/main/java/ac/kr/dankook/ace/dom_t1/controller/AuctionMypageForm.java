// 7.11 생성 : mypage 구현에서 url을 넘겨주기 위한 DTO 생성 
package ac.kr.dankook.ace.dom_t1.controller;



import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionMypageForm{

    private int id;
    private String username;
    private String url;
    private LocalDateTime createDate;
}