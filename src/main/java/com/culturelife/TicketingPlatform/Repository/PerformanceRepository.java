package com.culturelife.TicketingPlatform.Repository;

import com.culturelife.TicketingPlatform.Entity.Performance;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PerformanceRepository {
    private final EntityManager em;

    public void save(Performance performance) {
        em.persist(performance);
    }

    public Performance findById(Long performanceId) {
        Performance performance = em.createQuery("select distinct p from Performance p left join fetch p.performanceSeats where p.id = :id", Performance.class)
                .setParameter("id", performanceId)
                .getSingleResult();

        return performance;
    }

    public List<Performance> findPerformancePage(int page, int pageCount) {
        List<Performance> postList = em.createQuery("select p from Performance p", Performance.class)
                .setFirstResult(page)
                .setMaxResults(pageCount)
                .getResultList();

        return postList;
    }

    public List<Performance> findByPerformanceName(String performanceName) {
        List<Performance> searchPerformanceList = null;

        searchPerformanceList = em.createQuery("select distinct p from Performance p where p.performanceName like :performanceName", Performance.class)
                .setParameter("performanceName", "%" + performanceName + "%").getResultList();

        return searchPerformanceList;
    }

    public List<Performance> findAll() {
        List<Performance> performanceList = em.createQuery("select distinct p from Performance p left join fetch p.performanceSeats", Performance.class)
                .getResultList();

        return performanceList;
    }

    public Long counts() {
        Long count = em.createQuery("select COUNT(p) from Performance p", Long.class)
                .getSingleResult();

        return count;
    }

    public Long deletePerformance(Performance performance) {
        Long id = performance.getId();
        em.remove(performance);
        em.flush();
        em.clear();
        return id;
    }

}
