package com.culturelife.TicketingPlatform.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String seat;

    @Column(nullable = false)
    private Boolean isBooked = false;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @OneToOne(fetch=FetchType.LAZY, mappedBy = "seat")
    private Ticket ticket;
}
