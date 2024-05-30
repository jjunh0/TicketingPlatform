package com.culturelife.TicketingPlatform.Repository;

import com.culturelife.TicketingPlatform.Entity.Order;
import com.culturelife.TicketingPlatform.Entity.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Transactional
public class TicketRepository {
    private final EntityManager em;
    public void save(Ticket ticket) { em.persist(ticket); }
}
