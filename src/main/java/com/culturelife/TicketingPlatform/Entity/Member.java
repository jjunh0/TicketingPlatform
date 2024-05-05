package com.culturelife.TicketingPlatform.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String memberId;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false, length = 10)
    private String memberName;

    @Column(nullable = false, length = 50, unique = true)
    private String memberEmail;

    @JsonManagedReference
    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "member")
    private List<Ticket> ticketList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Question> questionList = new ArrayList<>();

    @Column(nullable = false)
    private boolean universityAttendance;

    private LocalDateTime memberCreateDate;

    private LocalDateTime memberUpdateDate;
}
