package ac.kr.dankook.ace.dom_t1.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SiteuserCreateForm {
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]*$", message = "ID는 영문과 숫자의 조합이어야 합니다.") // id 생성시에 영문 , 숫자 조합으로만 가능하도록 Pattern Annotation 추가
    @Size(min = 9, max = 25) // id의 최소-최대 길이 조절 ( 조정 필요 )
    @NotEmpty(message = "사용자닉네임은 필수항목입니다.") // 
    private String username;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]*$", message = "PASSWORD는 영문과 숫자의 조합이어야 합니다.") // Psw 생성시에 영문 , 숫자 조합으로만 가능하도록 함 
    @Size(min = 9, max = 25) // Psw의 최소-최대 길이 조절 ( 조정 필요 )
    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String psw_check1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String psw_check2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email // 이메일 형식과 일치하는지 검증하는 Annotaion 
    private String email;

    @Size(min = 9, max = 25)
    @NotEmpty(message = "비밀번호를 찾기 위해서는 비밀번호 물음에 대한 힌트가 필요합니다.") // 힌트 부분 추가
    private String hint;

    @Size(min = 9, max = 25) // id의 최소-최대 길이 조절 ( 조정 필요 )
    @NotEmpty(message = "사용자ID는 필수항목입니다.") //
    private String nickname;
}
// 바밀번호 힌트 추가