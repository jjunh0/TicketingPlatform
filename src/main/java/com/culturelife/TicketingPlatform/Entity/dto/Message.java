package com.culturelife.TicketingPlatform.Entity.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String message;
    private String redirectUri;
}