package ac.kr.dankook.ace.dom_t1.controller;

import jakarta.validation.Valid;

import java.security.Principal;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ac.kr.dankook.ace.dom_t1.Model.Entity.SiteuserEntity;
import ac.kr.dankook.ace.dom_t1.Model.Service.SiteuserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;



@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class SiteuserController {
    private final SiteuserService siteuserService;

    @GetMapping("main")
    public String getMethodName() {
        // MyPage Request를 위한 {username} Pathvariable 필요...
        // PreAuthorize를 대체하기 위한 로직 필요...
        /*SiteuserEntity siteuserEntity = this.siteuserService.getUser(principal.getName());
        model.addAttribute("siteuserEntity", siteuserEntity);*/
        return "dom_main";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login_form";
    } // 로그인 화면 -> HTML 파일 생성 필요


    @GetMapping("/signup")
    public String signup(SiteuserCreateForm siteuserCreateForm,Model model) {
        model.addAttribute("siteuserCreateForm", siteuserCreateForm);
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid SiteuserCreateForm siteuserCreateForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return "signup_form"; // 회원가입 폼 필요 
    }

    if (!siteuserCreateForm.getPsw_check1().equals(siteuserCreateForm.getPsw_check2())) {
        bindingResult.rejectValue("psw_check2", "passwordInCorrect", //${psw_check2}와 일치하는지 여부 체크 부탁드립니다.
                "2개의 패스워드가 일치하지 않습니다.");
        return "signup_form"; // 패스워드 일치 여부 -> HTML 파일 생성 필요
    }

    try { // 이미 사용자의 Id 또는 
        siteuserService.create(siteuserCreateForm.getUsername(), 
                siteuserCreateForm.getEmail(), siteuserCreateForm.getPsw_check1(),siteuserCreateForm.getHint(),siteuserCreateForm.getNickname());
    }catch(DataIntegrityViolationException e) { // 사용자의 ID 또는 이메일 주소가 이미 존재할 경우에 예외가 발생하도록 하여 " 이미 등록된 사용자 입니다." 메시지가 나오도록 표시
        e.printStackTrace(); 
        bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
        return "signup_form";
    }catch(Exception e) {
        e.printStackTrace();
        bindingResult.reject("signupFailed", e.getMessage()); // 오류에 대한 메시지를 보내도록함 
        return "signup_form";
    }
    return "redirect:/user/login";
    }

    /*@GetMapping("/mypage")
    public String mypage(Model model, Principal principal)
    {
        SiteuserEntity siteuserEntity = this.siteuserService.getUser(principal.getName());
        model.addAttribute("siteuserEntity", siteuserEntity);
        return "MyPage";
    }*/
}
