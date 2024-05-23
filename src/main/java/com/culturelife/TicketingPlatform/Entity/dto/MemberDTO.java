package com.culturelife.TicketingPlatform.Entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private String memberEmail;
    private String memberName;
    private String memberId;
    private String password;
}
