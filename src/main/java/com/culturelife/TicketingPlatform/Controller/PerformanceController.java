package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("/list")
    public String list(Model model) {

        List<Performance> allPerformanceList = performanceService.findAllPerformance();
        model.addAttribute("performanceList", allPerformanceList);

        return "home"; // 임시(수정필요)
    }


    @GetMapping({"/list/{id}"})
    public String list(Model model, @RequestParam(value="page", defaultValue="1") int page) {

        Page<Performance> performancePage = null;
        performancePage = performanceService.searchPostPage(page);

        model.addAttribute("performancePage", performancePage);

        return "home"; // 임시(수정필요)
    }
}
