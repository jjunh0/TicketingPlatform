package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Entity.Seat;
import com.culturelife.TicketingPlatform.Repository.PerformanceRepository;
import com.culturelife.TicketingPlatform.Repository.SeatRepository;
import com.culturelife.TicketingPlatform.Entity.dto.PerformanceDTO;
import com.culturelife.TicketingPlatform.Entity.dto.SeatInfoDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class ConcertSeatService {
    private final SeatRepository seatRepository;
    private final PerformanceRepository performanceRepository;

    public Performance createPerformance(PerformanceDTO performanceDTO) {
        List<Seat> seats = new ArrayList<>();
        Performance performance = new Performance();
        performance.setPerformanceName(performanceDTO.getPerformanceName());
        performance.setPerformanceContents(performanceDTO.getPerformanceContents());
        performance.setPerformancePrice(performanceDTO.getPerformancePrice());
        performance.setPerformanceDate(performanceDTO.getPerformanceDate());
        for(int i = 1; i <= performanceDTO.getPerformanceCount(); i ++) {
            Seat seat = new Seat();
            seat.setPerformance(performance);
            seat.setIsBooked(false);
            seat.setSeatName(Integer.toString(i));
            seatRepository.save(seat);
            seats.add(seat);
        }
        performance.setPerformanceSeats(seats);
        performanceRepository.save(performance);
        return performance;
    }
    public PerformanceDTO readPerformanceById(Long performanceId) {
        Performance performance = performanceRepository.readById(performanceId);
        return PerformanceDTO.builder()
                .performanceId(performanceId)
                .performanceName(performance.getPerformanceName())
                .performanceContents(performance.getPerformanceContents())
                .performancePrice(performance.getPerformancePrice())
                .performanceCount(performance.getPerformanceCount())
                .performanceDate(performance.getPerformanceDate())
                .build();
    }

    public List<SeatInfoDTO> readSeatById(Long id) {
        List<Seat> seatList =  performanceRepository.readById(id).getPerformanceSeats();
        List<SeatInfoDTO> seatInfoDTO = new ArrayList<>();

        for(Seat s : seatList) {
            SeatInfoDTO seatInfo = SeatInfoDTO.builder()
                    .seatName(s.getSeatName())
                    .isBooked(s.getIsBooked())
                    .build();
            seatInfoDTO.add(seatInfo);
        }
        return seatInfoDTO;
    }
    public Boolean reserve(Long performanceId, String seatName) {
        Seat seat = seatRepository.readByIdAndName(performanceId, seatName);
        if(seat.getIsBooked()) {
            System.out.println("예약됨");
            return false;
        }
        seat.setIsBooked(true);
        return true;
    }


}
