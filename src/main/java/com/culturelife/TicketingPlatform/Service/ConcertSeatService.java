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

    public PerformanceDTO getPerformanceById(Long performanceId) {
        Performance performance = performanceRepository.findById(performanceId);
        return PerformanceDTO.builder()
                .performanceId(performanceId)
                .performanceName(performance.getPerformanceName())
                .performanceContents(performance.getPerformanceContents())
                .performancePrice(performance.getPerformancePrice())
                .performanceCount(performance.getPerformanceCount())
                .performanceDate(performance.getPerformanceDate())
                .build();
    }
    public List<SeatInfoDTO> getSeatById(Long id) {
        List<Seat> seatList =  performanceRepository.findById(id).getPerformanceSeats();
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
        Seat seat = seatRepository.findByIdAndName(performanceId, seatName);
        if(seat.getIsBooked()) {
            System.out.println("예약됨");
            return false;
        }
        seat.setIsBooked(true);
        return true;
    }


}
