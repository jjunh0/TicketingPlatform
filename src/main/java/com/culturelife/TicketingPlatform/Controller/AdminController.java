package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.dto.PerformanceDTO;
import com.culturelife.TicketingPlatform.Service.ConcertSeatService;
import com.culturelife.TicketingPlatform.Service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final PerformanceService performanceService;
    private final ConcertSeatService concertSeatService;


    @GetMapping("/admin/create")
    public String showCreateForm(Model model) {
        return "createPerformance";
    }

    @PostMapping("/admin/create")
    public String createPerformance(PerformanceDTO performanceDTO) {
        concertSeatService.createPerformance(performanceDTO);
        return "redirect:/admin/create";
    }
}
