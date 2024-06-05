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

    @GetMapping(value = {"/contestlist/{page}", "/contestlist"})
    public String list(Model model, @PathVariable(name="page", required = false) Integer page ) {
        if(page == null) {
            page = 1;
        }

        Page<Performance> performances = performanceService.readPostPage(page);
        int totalPages = performances.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("performancelist", performances);

        return "contestlist"; // 임시(수정필요)
    }


}
