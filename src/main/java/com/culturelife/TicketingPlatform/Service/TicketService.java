package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Seat;
import com.culturelife.TicketingPlatform.Entity.Ticket;
import com.culturelife.TicketingPlatform.Entity.dto.SeatInfoDTO;
import com.culturelife.TicketingPlatform.Entity.dto.TicketDTO;
import com.culturelife.TicketingPlatform.Repository.PerformanceRepository;
import com.culturelife.TicketingPlatform.Repository.SeatRepository;
import com.culturelife.TicketingPlatform.Repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;
    private final PerformanceRepository performanceRepository;
    private final SeatRepository seatRepository;
    public void makeTicket(Long performanceId, String seatName, Member member) {
        Ticket ticket = new Ticket();
        ticket.setTicketName(seatName);
        ticket.setTicketPrice(performanceRepository.readById(performanceId).getPerformancePrice());
        ticket.setPerformance(performanceRepository.readById(performanceId));
        ticket.setMember(member);
        ticket.setSeat(seatRepository.readByIdAndName(performanceId, seatName));
        ticketRepository.save(ticket);
    }
    public List<TicketDTO> getMemberTickets(Long memberId) {
        List<TicketDTO> ticketDTOList = new ArrayList<>();
        List<Ticket> ticketList = ticketRepository.findByMemberId(memberId);

        for(Ticket t : ticketList) {
            TicketDTO ticketDTO = TicketDTO.builder()
                    .memberId(memberId)
                    .performance(t.getPerformance())
                    .ticketPrice(t.getTicketPrice())
                    .seatName(t.getSeat().getSeatName())
                    .build();
            ticketDTOList.add(ticketDTO);
        }
        return ticketDTOList;
    }
}
