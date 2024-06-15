package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping(value = {"/contestlist", "/contestlist/"})
    public String getContestList(Model model) {

        return getContestListByPage(model, 1);
    }

    @GetMapping(value = {"/contestlist/page"})
    public String getContestListByPage(Model model, @RequestParam(value = "page", defaultValue = "1") int page ) {
        if(page <= 0)
            page = 1;

        Long pageCount = performanceService.count() / 10;

        if(page > pageCount) {
            page = pageCount.intValue();
        }

        PageRequest pageable = PageRequest.of(page - 1, 10);
        Page<Performance> performancePage = performanceService.readPostPage(page);

        int totalPages = performancePage.getTotalPages();


        model.addAttribute("totalPages", totalPages);
        model.addAttribute("performancelist", performancePage);
        model.addAttribute("currentPage", page);

        return "contestlist"; // 임시(수정필요)
    }


}
