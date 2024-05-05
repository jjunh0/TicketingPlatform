package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    @Transactional
    public Long createPerformance(Performance performance) {

        performanceRepository.save(performance);
        return performance.getId();
    }

    public Performance findPerformanceById(Long id) {
        return performanceRepository.findById(id);
    }

    public List<Performance> findAllPerformance() {
        return performanceRepository.findAll();
    }

    public List<Performance> findQuestionByPerformanceName(String performanceName) {
        return performanceRepository.findByPerformanceName(performanceName);
    }

    @Transactional
    public void updatePerformance(Long performanceId, Performance newPerformance) {
        Performance performance = performanceRepository.findById(performanceId);
        performance.setPerformanceName(newPerformance.getPerformanceName());
        performance.setPerformanceSeats(newPerformance.getPerformanceSeats());
        performance.setPerformanceContents(newPerformance.getPerformanceContents());
        performance.setPerformanceDate(newPerformance.getPerformanceDate());
    }
}
