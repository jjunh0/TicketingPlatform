package com.culturelife.TicketingPlatform.Entity;

import com.culturelife.TicketingPlatform.Entity.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private int orderPrice;

    @Column(nullable = false)
    private int orderCount;

    @OneToMany(mappedBy = "order")
    private List<Ticket> ticketList;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus; /*= OrderStatus.ORDER;*/

    private LocalDateTime memberCreateDate;

    private LocalDateTime memberUpdateDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
