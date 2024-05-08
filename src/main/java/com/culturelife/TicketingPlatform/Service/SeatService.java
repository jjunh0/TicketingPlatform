package com.culturelife.TicketingPlatform.Service;


import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Entity.Seat;
import com.culturelife.TicketingPlatform.Repository.PerformanceRepository;
import com.culturelife.TicketingPlatform.Repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final PerformanceRepository performanceRepository;

    @Transactional
    public void createSeat(Long performanceId, Seat seat) {
        Performance performance = performanceRepository.findById(performanceId);
        seat.setPerformance(performance);

        performance.getPerformanceSeats().add(seat);
        seatRepository.save(seat);
    }

    public List<Seat> findByPerformanceId(Long performanceId) {
        List<Seat> seatList = seatRepository.findByPerformanceId(performanceId);

        return seatList;
    }

}