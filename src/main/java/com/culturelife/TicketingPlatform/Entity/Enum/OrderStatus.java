package com.culturelife.TicketingPlatform.Entity.Enum;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDER("ORDER"),
    CANCEL("CANCEL");

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;;
    }

    private final String orderStatus;
}
