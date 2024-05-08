package com.culturelife.TicketingPlatform.Entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PerformanceDTO {
    private Long performanceId;
    private String performanceName;
    private String performanceContents;
    private int performancePrice;
    private int performanceCount;
    private LocalDateTime performanceDate;
}
