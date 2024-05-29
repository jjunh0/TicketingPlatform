package com.culturelife.TicketingPlatform.Service;

import com.culturelife.TicketingPlatform.Entity.Enum.OrderStatus;
import com.culturelife.TicketingPlatform.Entity.Member;
import com.culturelife.TicketingPlatform.Entity.Order;
import com.culturelife.TicketingPlatform.Entity.Seat;
import com.culturelife.TicketingPlatform.Entity.Ticket;
import com.culturelife.TicketingPlatform.Repository.OrderRepository;
import com.culturelife.TicketingPlatform.Repository.PerformanceRepository;
import com.culturelife.TicketingPlatform.Repository.SeatRepository;
import com.culturelife.TicketingPlatform.Repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final PerformanceRepository performanceRepository;
    private final SeatRepository seatRepository;
    public void makeOrderAndTicket(Long performanceId, String seatName, Member member) {
        Order order = new Order();
        order.setOrderPrice(performanceRepository.readById(performanceId).getPerformancePrice());
        order.setOrderCount(1);
        order.setOrderStatus(OrderStatus.ORDER);
        order.setMemberCreateDate(LocalDateTime.now());
        order.setMember(member);

        Ticket ticket = new Ticket();
        ticket.setTicketName(seatName);
        ticket.setTicketPrice(performanceRepository.readById(performanceId).getPerformancePrice());
        ticket.setOrder(order);
        ticket.setPerformance(performanceRepository.readById(performanceId));
        ticket.setMember(member);
        ticket.setSeat(seatRepository.readByIdAndName(performanceId, seatName));
        ticketRepository.save(ticket);
        order.getTicketList().add(ticket);
        orderRepository.save(order);
    }

}
