package com.culturelife.TicketingPlatform.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatInfoDTO {
    private Long id;
    private Long seat;
    private Boolean isBooked;
}
