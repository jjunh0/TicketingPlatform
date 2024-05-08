package com.culturelife.TicketingPlatform.controller;

import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Service.ConcertSeatService;
import com.culturelife.TicketingPlatform.dto.PerformanceDTO;
import com.culturelife.TicketingPlatform.dto.SeatInfoDTO;
import com.culturelife.TicketingPlatform.dto.SeatSelectionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConcertInfoController {

    private final ConcertSeatService concertSeatService;
    @GetMapping("/performances/{performanceId}")
    public String concertInfo(@PathVariable("performanceId") Long performanceId, Model model) {
        PerformanceDTO performanceDTO = concertSeatService.getPerformanceById(performanceId);
        model.addAttribute("performance", performanceDTO);
        return "concertInfo";
    }
    @GetMapping("/performances/{performanceId}/seats")
    public String concertSelect(@PathVariable("performanceId") Long performanceId, Model model) {
        List<SeatInfoDTO> seat = concertSeatService.getSeatById(performanceId);
        model.addAttribute("seat", seat.stream().sorted(Comparator.comparing(SeatInfoDTO::getSeatName)));
        return "seatSelect";
    }

    @PatchMapping("/seats/{performanceId}")
    public String seatReserve(@PathVariable("performanceId") Long performanceId, SeatSelectionDTO seatSelectionForm) {
        if(concertSeatService.reserve(performanceId, seatSelectionForm.getSeatName())) {
            System.out.println("예약 성공");
            return "redirect:/";
        }
        System.out.println("예약 실패");
        return "redirect:/performances/" + performanceId + "/seats";
    }


}
