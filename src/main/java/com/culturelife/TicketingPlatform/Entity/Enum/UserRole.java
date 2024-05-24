package com.culturelife.TicketingPlatform.Entity.Enum;

import lombok.Getter;
@Getter
public enum UserRole {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String role;
    UserRole(String role) {
        this.role = role;
    }
}
