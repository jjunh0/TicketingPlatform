package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Entity.Seat;
import com.culturelife.TicketingPlatform.Repository.PerformanceRepository;
import com.culturelife.TicketingPlatform.Service.PerformanceService;
import com.culturelife.TicketingPlatform.Service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("/list")
    public String list(Model model) {

        List<Performance> allPerformanceList = performanceService.findAllPerformance();
        model.addAttribute("performanceList", allPerformanceList);

        return "PerformanceList";
    }

}
