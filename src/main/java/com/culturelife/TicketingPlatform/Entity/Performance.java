package com.culturelife.TicketingPlatform.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
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

    @JsonManagedReference
    @OneToMany(mappedBy = "performance", fetch=FetchType.LAZY)
    private List<Seat> performanceSeats = new ArrayList<>();

    private LocalDateTime performanceDate;
}
