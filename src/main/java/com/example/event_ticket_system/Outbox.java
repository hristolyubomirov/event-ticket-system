package com.example.event_ticket_system;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="outbox_table", schema = "outbox_schema")
@Getter
@Setter
public class Outbox {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(
            name="id"
    )
    private Long id;
    @Column(
            name="book_id"
    )
    private String bookId;
    @Column(
            name="payload"
    )
    private String payload;
    @Column(
            name="topic"
    )
    private String topic;
    @Column(
            name="processed"
    )
    private boolean processed = false;
    @Column(
            name="date_now"
    )
    private LocalDate dateNow = LocalDate.now();
}
