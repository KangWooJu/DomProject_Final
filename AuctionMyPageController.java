package ac.kr.dankook.ace.dom_t1.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionRegisterEntity;
import ac.kr.dankook.ace.dom_t1.Model.Service.AuctionBidService;
import ac.kr.dankook.ace.dom_t1.Model.Service.AuctionRegisterService;
import ac.kr.dankook.ace.dom_t1.Model.Service.AuctionRequestService;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequestMapping("/mypage")
@RequiredArgsConstructor
@Controller
public class AuctionMyPageController {

    private final AuctionRegisterService auctionRegisterService;
    private final AuctionRequestService auctionRequestService;
    private final AuctionBidService auctionBidService;

    // 7.14 수정 
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{username}")
    public String mypage(Model model, @PathVariable("username") String username,AuctionRegisterForm auctionRegisterForm,@RequestParam(value="page", defaultValue="0") int page,Integer id) {

        AuctionRegisterEntity auctionRegisterEntity = this.auctionRegisterService.getAuctionRegisterEntity(id);
        model.addAttribute("auctionRegisterEntity",auctionRegisterEntity);
        // 사용자의 기본 정보를 model에 주입
        // 누적 판매 및 누적 구매에 관한 논의가 필요해 보임

        Page<AuctionRegisterEntity> pagingMine = this.auctionRegisterService.getListMine(username,page,10); // 자신이 등록한 거래글을 보여주는 page객체 
        model.addAttribute("idonas",pagingMine);
        model.addAttribute("currentPage",page); // 현재 페이지를 보여준다 
        model.addAttribute("totalPages",pagingMine.getTotalPages()); // 전체 페이지를 보여준다. 

        return "mypage";
    }

}