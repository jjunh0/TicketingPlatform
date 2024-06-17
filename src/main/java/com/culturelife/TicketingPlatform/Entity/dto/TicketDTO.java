package com.culturelife.TicketingPlatform.Entity.dto;

import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Entity.Seat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TicketDTO {
    private Long memberId;
    private int ticketPrice;
    private String seatName;
    private Performance performance;
}
