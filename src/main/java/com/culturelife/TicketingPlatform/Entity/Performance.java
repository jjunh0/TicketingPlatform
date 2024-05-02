package com.culturelife.TicketingPlatform.Entity;

import com.culturelife.TicketingPlatform.Entity.Enum.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "performance")
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performance_id")
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String performanceName;

    @Lob
    @Column(nullable = false)
    private String performanceContents;

    @Column(nullable = false)
    private int performancePrice;

    @Column(nullable = false)
    private int performanceCount;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "seat")
    private List<Seat> performanceSeats;

    private LocalDateTime performanceDate;
}
