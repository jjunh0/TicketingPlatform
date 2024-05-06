package com.culturelife.TicketingPlatform.Repository;

import com.culturelife.TicketingPlatform.Entity.Seat;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class SeatRepository {
    private final EntityManager em;
    public Seat findByIdAndName(Long id, Long name) {
        return em.createQuery("select s from Seat s where s.performance.id = :id and s.seat = :name", Seat.class)
                .setParameter("id", id)
                .setParameter("name", name)
                .getSingleResult();
    }

}
