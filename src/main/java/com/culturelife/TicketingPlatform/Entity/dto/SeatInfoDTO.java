package com.culturelife.TicketingPlatform.Entity.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatInfoDTO {
    private Long performanceId;
    private String seatName;
    private Boolean isBooked;
}
