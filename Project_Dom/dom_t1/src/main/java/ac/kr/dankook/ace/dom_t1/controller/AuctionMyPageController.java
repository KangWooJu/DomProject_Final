package ac.kr.dankook.ace.dom_t1.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionRegisterEntity;
import ac.kr.dankook.ace.dom_t1.Model.Entity.SiteuserEntity;
import ac.kr.dankook.ace.dom_t1.Model.Service.AuctionBidService;
import ac.kr.dankook.ace.dom_t1.Model.Service.AuctionRegisterService;
import ac.kr.dankook.ace.dom_t1.Model.Service.AuctionRequestService;
import ac.kr.dankook.ace.dom_t1.Model.Service.SiteuserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.*;

@RequestMapping("/mypage")
@RequiredArgsConstructor
@Controller
public class AuctionMyPageController {

    private final AuctionRegisterService auctionRegisterService;
    private final SiteuserService siteuserService;

    // 7.14 수정 

    //PreAuthorize -> username PathVariable 이용
    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public String mypagePreprocess(Principal principal, RedirectAttributes redirectAttributes)
    {
        SiteuserEntity siteuserEntity = this.siteuserService.getUser(principal.getName());
        redirectAttributes.addAttribute("username", siteuserEntity.getUsername());
        return "redirect:/mypage/{username}";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{username}")
    public String mypage(Model model, @PathVariable("username") String username,AuctionRegisterForm auctionRegisterForm, @RequestParam(value="page", defaultValue="0") int page) {

        SiteuserEntity siteuserEntity = siteuserService.getUser(username);
        model.addAttribute("siteuserEntity", siteuserEntity);
        // 사용자의 기본 정보를 model에 주입 (AuctionRegisterEntity -> SiteuserEntity)
        // 누적 판매 및 누적 구매에 관한 논의가 필요해 보임

        Page<AuctionRegisterEntity> pagingMine = this.auctionRegisterService.getListMine(username,page,10); // 자신이 등록한 거래글을 보여주는 page객체 
        model.addAttribute("currentPage", pagingMine);
        model.addAttribute("nope", page); // 현재 페이지를 보여준다
        model.addAttribute("totalPages", pagingMine.getTotalPages()); // 전체 페이지를 보여준다.

        return "MyPage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{username}")
    public String userModify(Model model, SiteuserCreateForm siteuserCreateForm,@PathVariable("username") String username,Principal principal, RedirectAttributes redirectAttributes){
        
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        } // Principal을 받지 못할 경우에 대한 예외 처리 
        // Principal 객체를 통해 사용자의 정보를 가져오는 방식
        SiteuserEntity siteuserEntity = this.siteuserService.getUser(principal.getName());
        // siteuserCreateForm 에 존재하는 정보를 재정의 
        siteuserCreateForm.setUsername(siteuserEntity.getUsername());
        siteuserCreateForm.setEmail(siteuserEntity.getEmail());
        siteuserCreateForm.setPsw_check1(siteuserEntity.getPsw());
        siteuserCreateForm.setHint(siteuserEntity.getHint());
        siteuserCreateForm.setNickname(siteuserEntity.getNickname());

        model.addAttribute("siteuserCreateForm", siteuserCreateForm);
        redirectAttributes.addAttribute("username", siteuserEntity.getUsername());
        return "modifyUser";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{username}")
    public String userModify(@Valid SiteuserCreateForm siteuserCreateForm, BindingResult bindingResult,Principal principal, @PathVariable("username") String username){
        if (bindingResult.hasErrors()) {
            return "modifyUser";
        }
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        } // Principal을 통해서 객체를 받지 못했을 경우에 대한 예외 처리 
        SiteuserEntity siteuserEntity = this.siteuserService.getUser(principal.getName());

        this.siteuserService.modify(siteuserCreateForm.getUsername(),siteuserCreateForm.getEmail(),siteuserCreateForm.getPsw_check1(),siteuserCreateForm.getHint(),siteuserEntity);
        return String.format("redirect:/mypage/%s", username); // siteuserService의 modify 메소드 사용 -> 서비스 파일 참고 
        // getUsername() 불필요 -> 우주님 수정예정
    }

}