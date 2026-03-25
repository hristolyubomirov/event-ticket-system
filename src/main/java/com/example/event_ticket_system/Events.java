package com.example.event_ticket_system;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name="event_table")
@Data
public class Events {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long eventId;
    private String eventName;
    private String category;
    private String location;
    private LocalDate eventDate;
    public Double price;
    public Long ticketsCount;

}
