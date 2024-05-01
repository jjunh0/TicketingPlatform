package com.culturelife.TicketingPlatform.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String ticketName;

    @Column(nullable = false)
    private int ticketPrice;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "seat_id", unique = true)
    private Seat seat;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
