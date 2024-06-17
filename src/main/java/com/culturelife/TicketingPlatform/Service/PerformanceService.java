package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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

    public Long count() {
        return performanceRepository.counts();
    }

    public Page<Performance> readPostPage(int page) {
        Long total = performanceRepository.counts();
        int startPage = 0;
        int remainPageCount = 3;
        int pageCount =3;

        startPage = (int) (total - page * 3);
        if(startPage < 0) {
            remainPageCount = remainPageCount + startPage;
            startPage = 0;
        }

        List<Performance> performanceList = performanceRepository.readPerformancePage(startPage, remainPageCount);
        Collections.reverse(performanceList);


        Pageable pageable = PageRequest.of(page-1 ,pageCount);
        PageImpl<Performance> performancePage = new PageImpl<>(performanceList, pageable, total);
        return performancePage;
    }

    public Performance readPerformanceById(Long id) {
        return performanceRepository.readById(id);
    }

    public List<Performance> readAllPerformance() {
        return performanceRepository.readAll();
    }

    public List<Performance> readPerformanceByName(String performanceName) {
        return performanceRepository.readByPerformanceName(performanceName);
    }

    @Transactional
    public void updatePerformance(Long performanceId, Performance newPerformance) {
        Performance performance = performanceRepository.readById(performanceId);
        performance.setPerformanceName(newPerformance.getPerformanceName());
        performance.setPerformanceSeats(newPerformance.getPerformanceSeats());
        performance.setPerformanceContents(newPerformance.getPerformanceContents());
        performance.setPerformanceDate(newPerformance.getPerformanceDate());
    }
}
