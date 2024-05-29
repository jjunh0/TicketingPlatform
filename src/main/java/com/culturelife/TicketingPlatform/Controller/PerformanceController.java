package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("/list")
    public String list(Model model) {

        List<Performance> allPerformanceList = performanceService.readAllPerformance();
        model.addAttribute("performanceList", allPerformanceList);

        return "home"; // 임시(수정필요)
    }


    @GetMapping("/contestlist")
    public String getContestList(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<Performance> performancePage = performanceService.readPostPage(page);
        model.addAttribute("performancelist", performancePage.getContent());
        model.addAttribute("totalPages", performancePage.getTotalPages());
        model.addAttribute("page", page);
        return "contestlist";
    }
}
