package com.culturelife.TicketingPlatform.Repository;

import com.culturelife.TicketingPlatform.Entity.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class TicketRepository {
    private final EntityManager em;
    public void save(Ticket ticket) { em.persist(ticket); }
    public List<Ticket> findByMemberId(Long memberId) {
        return em.createQuery("SELECT t FROM Ticket t WHERE t.member.id = :memberId", Ticket.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
