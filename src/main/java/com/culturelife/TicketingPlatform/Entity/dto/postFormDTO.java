package com.culturelife.TicketingPlatform.Entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class postFormDTO {
  private String postSubject;
  private String postContents;
}
