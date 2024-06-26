package ac.kr.dankook.ace.dom_t1.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuctionRegisterForm {
    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=200)
    private String title;
    
    @PositiveOrZero(message = "가격은 음수가되면 안됩니다.") // Psw 생성시에 영문 , 숫자 조합으로만 가능하도록 함 
    @NotNull
    private int price;
    
    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;

    @NotNull(message="종료시간은 필수항목입니다.")
    private LocalDate duedate;
    
    @NotNull
    private String category;
    
    @NotNull
    private String locationcode;
    // 사진등록 관련해서 DTO 추가 사항을 작성해주세요
    // 드래그로 카테고리 넣을 수 있도록 프론트 수정 및 DTO 부분 협의 부탁드립니다.
    // 민혁님이 추가해주시면 

    
}
