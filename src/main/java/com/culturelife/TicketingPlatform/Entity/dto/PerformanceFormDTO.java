package com.culturelife.TicketingPlatform.Entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PerformanceFormDTO {
    private String performanceName;
    private String performanceContents;
    private int performancePrice;
    private int performanceCount;
    private LocalDateTime performanceDate;

}
