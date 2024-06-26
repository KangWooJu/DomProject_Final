package ac.kr.dankook.ace.dom_t1.controller;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionRequestForm {


    @NotEmpty(message = "내용은 필수항목입니다.") // 댓글 부분에 들어갈만한 요소들에 대해 상의 
    private String content;
}
