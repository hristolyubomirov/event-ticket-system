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
@Table(name="event_table", schema = "events_schema")
@Data
public class Events implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long eventId;
    @Column(nullable = false)
    private String eventName;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private LocalDate eventDate;
    @Column(nullable = false)
    public Double price;
    @Column(nullable = false)
    public Long ticketsCount;

}
