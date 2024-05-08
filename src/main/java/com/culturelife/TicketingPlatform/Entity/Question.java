package com.culturelife.TicketingPlatform.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String questionSubject;

    @Column(length = 4000, nullable = false)
    private String questionContents;

    @JsonBackReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime questionCreateDate;

    private LocalDateTime questionUpdateDate;
}
