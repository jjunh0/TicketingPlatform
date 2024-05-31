package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.dto.TicketDTO;
import com.culturelife.TicketingPlatform.Repository.TicketRepository;
import com.culturelife.TicketingPlatform.Service.AuthenticatedUserService;
import com.culturelife.TicketingPlatform.Service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MyPageController {
    private final TicketService ticketService;
    private final AuthenticatedUserService authenticatedUserService;
    @GetMapping("/mypage")
    public String myPage(Model model) {
        Long memberId = authenticatedUserService.getCurrentId();
        List<TicketDTO> list = ticketService.getMemberTickets(memberId);
        model.addAttribute("ticketList", list);
        return "mypage";
    }
}
