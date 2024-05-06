package com.culturelife.TicketingPlatform.Repository;

import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Entity.Question;
import com.culturelife.TicketingPlatform.Entity.Seat;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PerformanceRepository {
    private final EntityManager em;
    public Performance findById(Long id) {
        return em.find(Performance.class, id);
    }

}
