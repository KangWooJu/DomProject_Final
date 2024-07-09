package ac.kr.dankook.ace.dom_t1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Dom_Controller {

    @Autowired
    private WebConfig webconfig;

    @GetMapping("/user/main")
    public String Dom_main(Model model)
    {
        return "dom_main";
    }

    @GetMapping("/user/login")
    public String Dom_login(Model model)
    {
        model.addAttribute("loginInputForm", new LoginInputForm("", ""));
        return "login_form";
    }

    @GetMapping("/user/signup")
    public String Dom_registration(Model model)
    {
        return "signup_form";
    }

    @PostMapping("/user/signup") 
    public String Dom_register(Model model)
    {
        return "redirect:/user/main";
    }

    @GetMapping("/user/mypage")
    public String Dom_mypage(Model model)
    {
        return "MyPage";
    }

    @GetMapping("/DomAuction/create")
    public String Dom_post_product(Model model, ProductPostForm productPostForm)
    {
        model.addAttribute("productPostForm", productPostForm);
        return "Auction_create";
    }

    @PostMapping("/DomAuction/create")
    public String Dom_postProduct(@ModelAttribute("productPostForm") ProductPostForm productPostForm, Model model)
    {
        System.out.println(productPostForm);
        // Form -> Entity
        return "redirect:/DomAuction/list";
    }

    @GetMapping("/user/pswchk")
    public String Dom_password_check(Model model)
    {
        return "password-check";
    }

    @GetMapping("request_form")
    public String Dom_request_form(Model model)
    {
        return "Request_form";
    }

    @GetMapping("/DomAuction/list")
    public String Dom_product_list(Model model)
    {
        return "AuctionList";
    }

    @GetMapping("/DomAuction/detail")
    public String Dom_product_detail(Model model)
    {
        return "Auction_detail";
    }

    @PostMapping("main/search")
    public String Dom_main_search(@ModelAttribute("searchForm") SearchForm searchForm)
    {
        System.out.println(searchForm.getInput());
        return "dom_main";
    }

    @PostMapping("login/submit")
    public String DOM_Login_Submit(@ModelAttribute LoginInputForm inputForm, Model model){
        model.addAttribute("loginInputForm", new LoginInputForm("", ""));
        System.out.println(inputForm.toString());
        return "dom_main";
    }

    @GetMapping("search") // 옥션의 리스트를 보여주는 메소드 
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page, @RequestParam( value="input", defaultValue="") String input) {
        System.out.println(input);
        return "AuctionList";
    }
    
}
