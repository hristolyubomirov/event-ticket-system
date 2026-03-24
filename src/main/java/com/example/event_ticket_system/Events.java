package com.example.event_ticket_system;

import jakarta.persistence.*;

@Entity
@Table(name="event_table")
public class Events {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long eventId;
    String eventName;
    String category;
    String location;
    double price;
    long ticketsCount;

}
