package com.example.event_ticket_system;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingTicketRepo extends JpaRepository<BookingTicket,Long> {
    List<BookingTicket> findByUser_UserId(Long userId);
}
