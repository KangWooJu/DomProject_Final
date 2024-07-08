package ac.kr.dankook.ace.dom_t1.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Data
@Setter
@Getter
public class LoginInputForm {
    private String emailInput;
    private String passwordInput;
    public LoginInputForm(String emailInput, String passwordInput) {
        this.emailInput = emailInput;
        this.passwordInput = passwordInput;
    }
}
