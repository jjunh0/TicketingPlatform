package com.culturelife.TicketingPlatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConcertInfoController {
    @GetMapping("ConcertInfo")
    public String concertInfo() {
        return "concertinfo";
    }
}
