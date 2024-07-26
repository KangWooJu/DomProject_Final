package ac.kr.dankook.ace.dom_t1.controller;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
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

// 7.11 수정 사항 : MyPageController.java 생성 
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Controller
public class AuctionMyPageController {

    private final AuctionRegisterService auctionRegisterService;
    private final SiteuserService siteuserService;
    
   

    // 7.14 수정 : 마이페이지 url 생성 
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{username}")
    public String mypage(Model model,@PathVariable("username") String username,AuctionRegisterForm auctionRegisterForm,@RequestParam(value="page", defaultValue="0") int page,Principal principal) {

        SiteuserEntity siteuserEntity = this.siteuserService.getUser(principal.getName());
         // 7.22 수정 : 현재 접속자와 사용자가 일치하는지 여부

        model.addAttribute("siteuserEntity",siteuserEntity);
        // 사용자의 기본 정보를 model에 주입
        // 누적 판매 및 누적 구매에 관한 논의가 필요해 보임

        Page<AuctionRegisterEntity> pagingMine = this.auctionRegisterService.getListMine(username,page,10); // 자신이 등록한 거래글을 보여주는 page객체 : 7/22 username을 id로 수정
        model.addAttribute("Registerlist",pagingMine); // 사용자가 작성한 경매 글 목록을 포함하는 페이지 객체
        model.addAttribute("currentPage",page); // 현재 사용자가 보고 있는 페이지 번호
        model.addAttribute("totalPages",pagingMine.getTotalPages()); // 전체 페이지 수 
        return "mypage";
    }

    // 7.22 수정사항 : userModify 메소드 생성 -> 사용자의 기본 정보를 수정할 수 있는 기능 
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{username}")
    public String userModify(SiteuserCreateForm siteuserCreateForm,@PathVariable("username") String username,Principal principal){

        
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
        return "userModify";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{username}")
    public String userModify(@Valid SiteuserCreateForm siteuserCreateForm,BindingResult bindingResult,Principal principal,@PathVariable("username") String username){
        if (bindingResult.hasErrors()) {
            return "userModify";
        }
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        } // Principal을 통해서 객체를 받지 못했을 경우에 대한 예외 처리 
        SiteuserEntity siteuserEntity = this.siteuserService.getUser(principal.getName());

        // username 변경 필요 
        this.siteuserService.modify(siteuserCreateForm.getUsername(),siteuserCreateForm.getHint(),siteuserCreateForm.getEmail(),siteuserCreateForm.getPsw_check1(),siteuserEntity);
        return String.format("redirect:/mypage/%s",username); // siteuserService의 modify 메소드 사용 -> 서비스 파일 참고 
    }
}