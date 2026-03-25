package com.example.event_ticket_system;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name="booking_table")
@Data
public class BookingTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;
    @ManyToOne
    @JoinColumn(name = "eventId")
    private Events event;
    private Long seatNumber;
    @Enumerated(EnumType.STRING)
    private TicketsType ticketsType;

}



