package com.example.event_ticket_system;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name="event_table")
@Data
public class Events implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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
