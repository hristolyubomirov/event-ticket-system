package com.example.event_ticket_system;

import jakarta.persistence.*;

@Entity
@Table(name="ticket_table")
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long ticketId;
    long seatNumber;
    long ticketsCount;
    double ticketPrice;
    TicketsType ticketsType;

}



