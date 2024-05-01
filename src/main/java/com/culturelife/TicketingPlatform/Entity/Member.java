package com.culturelife.TicketingPlatform.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "member")
    private List<Order> orderList;

    @OneToMany(mappedBy = "member")
    private List<Ticket> ticketList;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Question> questionList;

    @Column(nullable = false)
    private boolean universityAttendance;

    private LocalDateTime memberCreateDate;

    private LocalDateTime memberUpdateDate;
}
