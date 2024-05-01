package com.culturelife.TicketingPlatform.controller;

import com.culturelife.TicketingPlatform.Service.ConcertSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConcertInfoController {

//    private final ConcertSeatService concertSeatService;
//    @Autowired
//    public ConcertInfoController(ConcertSeatService concertSeatService) {
//        this.concertSeatService = concertSeatService;
//    }
    @GetMapping("/concert-info")
    public String concertInfo() {
        System.out.println("concert-info");
        return "concertinfo";
    }
    @GetMapping("/concert-select")
    @ResponseBody
    public String concertSelect(@RequestParam Integer id) {
        return Integer.toString(id);
    }

}
