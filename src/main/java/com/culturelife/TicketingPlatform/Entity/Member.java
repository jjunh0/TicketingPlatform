package com.culturelife.TicketingPlatform.Entity;

import com.culturelife.TicketingPlatform.Entity.Enum.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    @Column(nullable = false, length = 70)
    private String password;

    @Transient
    private String repeatPassword;

    @Column(nullable = false, length = 16)
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
    private List<Post> postList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Column(nullable = false)
    private boolean universityAttendance;

    private LocalDateTime memberCreateDate;

    private LocalDateTime memberUpdateDate;

    // 권한 관련 추가
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles = new ArrayList<>();
}
