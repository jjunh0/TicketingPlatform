package com.culturelife.TicketingPlatform.Controller;

import com.culturelife.TicketingPlatform.Entity.dto.Message;
import com.culturelife.TicketingPlatform.Service.ConcertSeatService;
import com.culturelife.TicketingPlatform.Entity.dto.PerformanceDTO;
import com.culturelife.TicketingPlatform.Entity.dto.SeatInfoDTO;
import com.culturelife.TicketingPlatform.Entity.dto.SeatSelectionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConcertInfoController {

    private final ConcertSeatService concertSeatService;
    @GetMapping("/performances/{performanceId}")
    public String concertInfo(@PathVariable("performanceId") Long performanceId, Model model) {
        PerformanceDTO performanceDTO = concertSeatService.readPerformanceById(performanceId);
        model.addAttribute("performance", performanceDTO);
        return "book";
    }
    @GetMapping("/performances/{performanceId}/seats")
    public String concertSelect(@PathVariable("performanceId") Long performanceId, Model model) {
        List<SeatInfoDTO> seat = concertSeatService.readSeatById(performanceId);
        model.addAttribute("seatlist", seat.stream().sorted(Comparator.comparing((SeatInfoDTO::getSeatName))));
        return "seatTemp";
    }

    @PostMapping("/seats/{performanceId}")
    public ModelAndView seatReserve(@PathVariable("performanceId") Long performanceId, SeatSelectionDTO seatSelectionForm, ModelAndView mav) {
        if(concertSeatService.reserve(performanceId, seatSelectionForm.getSeatName())) {
            mav.addObject("data", new Message("예약 성공.", "/performances/"+performanceId));
            mav.setViewName("/common/message");
            System.out.println("예약 성공");
            return mav;
        }
        System.out.println("예약 실패");
        mav.addObject("data", new Message("이미 선택된 좌석입니다.", "/performances/"+performanceId+"/seats"));
        mav.setViewName("/common/message");
        return mav;

    }



}
