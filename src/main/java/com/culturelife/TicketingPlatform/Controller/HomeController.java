package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homePage() {
        return "home";
    }
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    @GetMapping("/contestlist")
    public String concertList() {
        return "contestlist";
    }

    @GetMapping("/community")
    public String community() {
        return "community";
    }
    @GetMapping("/signinup")
    public String signinup(Model model) {
        model.addAttribute("member", new MemberDTO());
        return "signinup";
    }

}
