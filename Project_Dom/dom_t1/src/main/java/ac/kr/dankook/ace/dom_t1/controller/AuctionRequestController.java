package ac.kr.dankook.ace.dom_t1.controller;


import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionRegisterEntity;
import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionRequestEntity;
import ac.kr.dankook.ace.dom_t1.Model.Entity.SiteuserEntity;
import ac.kr.dankook.ace.dom_t1.Model.Service.AuctionRegisterService;
import ac.kr.dankook.ace.dom_t1.Model.Service.AuctionRequestService;
import ac.kr.dankook.ace.dom_t1.Model.Service.SiteuserService;

@RequestMapping("DomAuction/detail/request")
@RequiredArgsConstructor
@Controller
public class AuctionRequestController {

    private final AuctionRegisterService auctionRegisterService;
    private final AuctionRequestService auctionRequestService;
    private final SiteuserService siteuserService;

    // 댓글 작성 
    @PreAuthorize("isAuthenticated()") // 로그인된상태 ( isAuthenticated = true 인 상태 ) 에서만 해당 url 사용가능 
    @PostMapping("/create/{username}") // URL 요청시에 createRequest 매서드가 호출되도록 @PostMapping 
    public String createRequest(Model model,@PathVariable("username") String username, @Valid AuctionRequestForm auctionRequestForm, BindingResult bindingResult, Principal principal) // content ( 댓글 내용 ) 를 requestparam으로 넘겨줌 , 시큐리티의 principal 객체 생성 
    {   
        int ids = Integer.parseInt(username);
        AuctionRegisterEntity auctionRegisterEntity = this.auctionRegisterService.getAuctionRegisterEntity(ids); // username 받아오기 
        SiteuserEntity siteuserEntity = this.siteuserService.getUser(principal.getName()); // principal은 siteuserEntity객체의 getUsername() 메소드를 호출
        if (bindingResult.hasErrors()) { //valid 검증에 맞지 않을 경우 예외 처리를 통해 Auction_detial.html 을 출력하도록 한다. 
            model.addAttribute("auctionRegisterEntity", auctionRegisterEntity);
            return "Auction_detail"; // Auction_detail.html 파일안에 예외 처리 필요 , 참고 ( 템플릿 수정 ) : https://wikidocs.net/161911
        }
        AuctionRequestEntity auctionRequestEntity = this.auctionRequestService.createRequest(auctionRegisterEntity, auctionRequestForm.getContent(),siteuserEntity);
        return String.format("redirect:/DomAuction/detail/"+auctionRegisterEntity.getId());
    }// 앵커기능 추가 -> 첫번째 %s : 글작성자의 이름을 가져옴 , 두번쨰 %s : 댓글 작성자의 이름을 가져옴 

    // 댓글 수정 ( Get )
    @PreAuthorize("isAuthenticated()") 
    @GetMapping("/modify/{username}")
    public String requestModify(AuctionRequestForm auctionRequestForm,@PathVariable("username") String username, Principal principal){
        AuctionRequestEntity auctionRequestEntity = this.auctionRequestService.getAuctionRequestEntity(username); // Username을 통해 수정자와 작성자 매핑
        if(!auctionRequestEntity.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다."); // 다르다면 수정권한 x
        }
        auctionRequestForm.setContent(auctionRequestEntity.getContent()); // 같다면 getContent를 통해 작성할 수 있도록함 
        return "Request_form";
    }

    // 댓글 수정 ( Post )
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{username}")
    public String requestModify(@Valid AuctionRequestForm auctionRequestForm, BindingResult bindingResult, @PathVariable("username") String username, Principal principal){

        if(bindingResult.hasErrors()){ // Valid 양식에 맞지 않는경우 예외 처리 
            return "Request_form";
        }
        AuctionRequestEntity auctionRequestEntity = this.auctionRequestService.getAuctionRequestEntity(username); // username으로 작성자 찾기
        if(!auctionRequestEntity.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다."); // username과 작성자가 일치 하지 않으면 권한x
        }
        this.auctionRequestService.modify(auctionRequestEntity,auctionRequestForm.getContent()); // 일치하면 modify 모듈 실행 
        return String.format("redirect:DomAuction/detail/%s#request_%s",auctionRequestEntity.getAuctionregisterentity().getUsername(),auctionRequestEntity.getUsername()); // 수정 끝나면 redirect
    } // 앵커기능 추가 

    // 댓글 삭제 ( Get )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{username}")
    public String requestDelete(Principal principal, @PathVariable("username") String username){
        AuctionRequestEntity auctionRequestEntity = this.auctionRequestService.getAuctionRequestEntity(username);
        if(!auctionRequestEntity.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다."); // username과 작성자가 일치 하지 않으면 권한x
        }
        this.auctionRequestService.delete(auctionRequestEntity); // 일치하면 delete 모듈 실행 
        return String.format("redirect/DomAuction/detial/%s",auctionRequestEntity.getAuctionregisterentity().getUsername()); // 수정 끝나면 redirect 
    }

    
}
