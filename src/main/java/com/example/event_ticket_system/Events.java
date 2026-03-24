package com.example.event_ticket_system;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="event_table")
@Data
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
