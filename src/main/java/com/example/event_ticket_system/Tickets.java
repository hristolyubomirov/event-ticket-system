package com.example.event_ticket_system;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="ticket_table")
@Data
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ticketId;
    Long seatNumber;
    long ticketsCount;
    double ticketPrice;
    @Enumerated(EnumType.STRING)
    private TicketsType ticketsType;

}



