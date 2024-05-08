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

    public void save(Seat seat) {
        em.persist(seat);
    }

    public Seat findById(Long seatId) {
        return em.find(Seat.class, seatId);
    }

    public List<Seat> findByPerformanceId(Long performanceId) {
        return em.createQuery("select s from Seat s where s.performance.id = :performanceId", Seat.class)
                .setParameter("performanceId", performanceId)
                .getResultList();
    }

    public List<Seat> findAll() {
        return em.createQuery("select s from Seat s", Seat.class)
                .getResultList();
    }

    public Long deleteSeat(Seat seat) {
        Long id = seat.getId();
        em.remove(seat);
        em.flush();
        em.clear();
        return id;
    }
    public Seat findByIdAndName(Long performanceId, String seatName) {
        return em.createQuery("select s from Seat s where s.performance.id = :id and s.seatName = :name", Seat.class)
                .setParameter("id", performanceId)
                .setParameter("name", seatName)
                .getSingleResult();
    }

}
