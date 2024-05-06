package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Performance;
import com.culturelife.TicketingPlatform.Entity.Seat;
import com.culturelife.TicketingPlatform.Repository.PerformanceRepository;
import com.culturelife.TicketingPlatform.Repository.SeatRepository;
import com.culturelife.TicketingPlatform.dto.SeatInfoDTO;
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

    public Performance getPerformanceById(Long id) {
        return performanceRepository.findById(id);
    }
    public List<SeatInfoDTO> getSeatById(Long id) {
        List<Seat> seatList =  performanceRepository.findById(id).getPerformanceSeats();
        List<SeatInfoDTO> seatInfoDTO = new ArrayList<>();

        for(Seat s : seatList) {
            SeatInfoDTO seatInfo = SeatInfoDTO.builder()
                    .seat(s.getSeat())
                    .isBooked(s.getIsBooked())
                    .build();
            seatInfoDTO.add(seatInfo);
        }
        return seatInfoDTO;
    }
    public Boolean reserve(Long id, Long seatNum) {
        Seat seat = seatRepository.findByIdAndName(id, seatNum);
        if(seat.getIsBooked()) {
            System.out.println("예약됨");
            return false;
        }
        seat.setIsBooked(true);
        return true;
    }


}
