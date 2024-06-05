package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Entity.dto.MemberDTO;
import com.culturelife.TicketingPlatform.Service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PerformanceService performanceService;

    @GetMapping(value = {"/", "/home"})
    public String homePage(Model model) {

        Page<Performance> performances = performanceService.readPostPage(1);
        int totalPages = performances.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("performancelist", performances);
        return "home";
    }

    @GetMapping("/community")
    public String community() {
        return "redirect:/community/1";
    }
    @GetMapping("/signinup")
    public String signinup(Model model) {
        model.addAttribute("member", new MemberDTO());
        return "signinup";
    }

}
