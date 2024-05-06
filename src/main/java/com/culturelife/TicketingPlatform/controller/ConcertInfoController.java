package com.culturelife.TicketingPlatform.controller;

import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Service.ConcertSeatService;
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
    @GetMapping("/concertInfo")
    public String concertInfo(@RequestParam Long id, Model model) {
        Performance performance = concertSeatService.getPerformanceById(id);
        model.addAttribute("name", performance.getPerformanceName());
        model.addAttribute("contents", performance.getPerformanceContents());
        model.addAttribute("price", performance.getPerformancePrice());
        model.addAttribute("date", performance.getPerformanceDate());
        return "concertInfo";
    }
    @GetMapping("/concertInfo/seat")
    @ResponseBody
    public String concertSelect(@RequestParam Long id, Model model) {
        List<SeatInfoDTO> seat = concertSeatService.getSeatById(id);
        model.addAttribute("seat", seat.stream().sorted(Comparator.comparing(SeatInfoDTO::getSeat)));
        return "seatSelect";
    }

    @PostMapping("/concertInfo/seatReserve")
    public String seatReserve(SeatSelectionDTO seatSelectionForm) {
        if(concertSeatService.reserve(seatSelectionForm.getId(), seatSelectionForm.getSeatId())) {
            System.out.println("예약 성공");
            return "redirect:/concertInfo";
        }
        System.out.println("예약 실패");
        return "redirect:/concertInfo/seatReserve"
    }


}
