package ac.kr.dankook.ace.dom_t1.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionBidEntity;
import ac.kr.dankook.ace.dom_t1.Model.Entity.AuctionRegisterEntity;
import ac.kr.dankook.ace.dom_t1.Model.Entity.SiteuserEntity;
import ac.kr.dankook.ace.dom_t1.Model.Service.AuctionBidService;
import ac.kr.dankook.ace.dom_t1.Model.Service.AuctionRegisterService;
import ac.kr.dankook.ace.dom_t1.Model.Service.SiteuserService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
@Controller
public class AuctionListController {

    private final AuctionRegisterService auctionRegisterService;
    private final SiteuserService siteuserService;
    private final AuctionBidService auctionBidService;
    private final String filepath = "C:/Dom/image/";

    @GetMapping("/DomAuction/list") // 옥션의 리스트를 보여주는 메소드 
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, @RequestParam( value="input", defaultValue="") String input) {
        Page<AuctionRegisterEntity> paging = this.auctionRegisterService.getList(page,input); // 페이지,input(검색기능)을 받아온 후에 모델에 넘겨주기
        model.addAttribute("paging", paging); // 페이징 모델 Add
        model.addAttribute("input",input); // input 모델 Add
        
        return "AuctionList"; 
    }

    @GetMapping("/DomAuction/list/category") // 6.28 추가 : 카테고리별 리스트 페이지 생성
    public String listByCategory(Model model,
                                 @RequestParam(value="page", defaultValue="0") int page, // int 타입 페이지를 HTML 넘겨준다
                                 @RequestParam(value="category") String category) { // String 타입의 category를 넘겨준다.
        Page<AuctionRegisterEntity> paging = this.auctionRegisterService.getListByCategory(page, category); // category 별로 찾을 수 있도록 하기 -> Service.getListByCategory 메소드 사용
        model.addAttribute("paging", paging);
        model.addAttribute("category", category);
        return "AuctionList";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/DomAuction/detail/{id}") // 옥션의 리스트 중 하나를 클릭했을 때 얻을 수 있는 화면 : 상세 페이지 -> HTML 내부에 링크 추가 필요 ( 질문 목록에 링크 추가하기 ): https://wikidocs.net/161302
    public String detail(Model model, @PathVariable("id") String id , AuctionRequestForm auctionRequestForm,AuctionBidForm bidform) {
        int ids = Integer.parseInt(id);
        AuctionRegisterEntity auctionRegisterEntity = this.auctionRegisterService.getAuctionRegisterEntity(ids); // 등록Service의 get 메소드 사용 
        model.addAttribute("auctionRegisterEntity",auctionRegisterEntity); // 모델에 넣어주기 
        
        String[] links = auctionRegisterEntity.getLink().split(",");
        System.out.println(links[0]);
        model.addAttribute("bidform", bidform);
        model.addAttribute("links", links);
        return "Auction_detail";
    }

    @PreAuthorize("isAuthenticated()") 
    @GetMapping("/DomAuction/create") // 글 작성 페이지 : Get 방식으로 화면 띄우기 
    public String auctionRegisterCreate(AuctionRegisterForm auctionRegisterForm){
        return "Auction_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/DomAuction/create") // 글 작성 페이지 : 
    public String auctionRegisterCreate(@Valid AuctionRegisterForm auctionRegisterForm , BindingResult bindingResult,Principal principal,@RequestPart("img1") MultipartFile file1,@RequestPart("img2") MultipartFile file2,@RequestPart("img3") MultipartFile file3){ 
        if (bindingResult.hasErrors()) { //valid에 맞지 않을 경우 리턴
            return "Auction_create"; // 검증 실패시에 대한 오류 처리 주석 필요 -> 
        }
        if(file1.isEmpty()||file2.isEmpty()||file3.isEmpty()){
            return "Auction_create";
        }
        String f1 = filepath+file1.getOriginalFilename();
        String f2 = filepath+file2.getOriginalFilename();
        String f3 = filepath+file3.getOriginalFilename();
        try{
        file1.transferTo(new File(f1));
        file2.transferTo(new File(f2));
        file3.transferTo(new File(f3));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fa = file1.getOriginalFilename()+","+file2.getOriginalFilename()+","+file3.getOriginalFilename();
        SiteuserEntity siteuserEntity = this.siteuserService.getUser(principal.getName());
        this.auctionRegisterService.AuctionRegisterCreate(auctionRegisterForm.getTitle(), auctionRegisterForm.getContent(),siteuserEntity, fa,auctionRegisterForm.getPrice(),auctionRegisterForm.getDuedate(),auctionRegisterForm.getCategory()); // AuctionRegisterService에 파라미터를 전달하고 메소드 시행 , principal객체를 통해서 사용자명을 얻은 후에 SiteuserEntity를 조회하여 옥션 등록글 저장시에 함께 저장
        return "redirect:/DomAuction/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{username}") //게시글 수정 모듈 : GetMapping ( 화면 )
    public String AuctionRegisterModify(AuctionRegisterForm auctionRegisterForm, @PathVariable("username") String username, Principal principal)
    {
        int ids = Integer.parseInt(username);
        AuctionRegisterEntity auctionRegisterEntity = this.auctionRegisterService.getAuctionRegisterEntity(ids); // auctionRegisterService 객체 받아오기 -> username 받아오기
        if(!auctionRegisterEntity.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다."); 
        }
        // 글쓴이 = 사용자가 같은 경우 성공 -> 
        auctionRegisterForm.setTitle(auctionRegisterEntity.getTitle()); // 글 제목 받아오기 
        auctionRegisterForm.setContent(auctionRegisterEntity.getContent()); // 글 내용 받아오기 
        return "Auction_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{username}") // 게시물 수정 모듈 : Post요청 처리 ( 전달 ) , AuctionRegisterForm 의 데이터를 검증 , 수정자와 작성자가 동일한지 검증
    public String AuctionRegisterModify(@Valid AuctionRegisterForm auctionRegisterForm , BindingResult bindingResult , Principal principal , @PathVariable("username") String username)
    {
        if (bindingResult.hasErrors()) { 
            return "Auction_create";
        }
        int ids = Integer.parseInt(username);
        AuctionRegisterEntity auctionRegisterEntity = this.auctionRegisterService.getAuctionRegisterEntity(ids);
        if (!auctionRegisterEntity.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다."); // 수정권한이 없으면 메시지 띄우기 
        } // 검증 성공시 ReigsterService의 modify 메소드 호출 -> 등록글 수정
        this.auctionRegisterService.modify(auctionRegisterEntity, auctionRegisterForm.getTitle(), auctionRegisterForm.getContent(),auctionRegisterForm.getPrice());
        return String.format("redirect:/question/detail/%s", username);
    }// 수정이 완료되면 redirect ( 등록 상세 페이지 )

    // 글 삭제 하기 ( GET )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{username}")
    public String AuctionReigsterDelete(Principal principal, @PathVariable("username") String username){
        int ids = Integer.parseInt(username);
        AuctionRegisterEntity auctionRegisterEntity = this.auctionRegisterService.getAuctionRegisterEntity(ids); // 이름 받아오기 
        if(!auctionRegisterEntity.getAuthor().getUsername().equals(principal.getName())){ // -> 삭제자 와 글쓴이가 같은지 확인 
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.");
            
        }
        this.auctionRegisterService.delete(auctionRegisterEntity); // 같다면 delete 실행 : HTML 파일 참고 필요 ( 버튼 추가하고 서비스와 컨트롤러 수정하기 ) https://wikidocs.net/162413
        return "redirect:/DomAuction/list";

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/DomAuction/bid")
    public String placeBid(@Valid @ModelAttribute("order") AuctionBidForm auctionBidForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return String.format("redirect:/DomAuction/detail/%s", auctionBidForm.getId());
        }
        if(auctionBidForm.getBidAmount() > auctionRegisterService.getAuctionRegisterEntity(Integer.parseInt(auctionBidForm.getId())).getHighestprice()){
            auctionRegisterService.changehighest(auctionRegisterService.getAuctionRegisterEntity(Integer.parseInt(auctionBidForm.getId())), auctionBidForm.getBidAmount(), principal.getName());
        }
        SiteuserEntity bidder = siteuserService.getUser(principal.getName());
        auctionBidService.createAuctionBid(auctionBidForm.getId(), bidder, auctionBidForm.getBidAmount());
        return String.format("redirect:/DomAuction/detail/%s", auctionBidForm.getId());
    }
        
    
}