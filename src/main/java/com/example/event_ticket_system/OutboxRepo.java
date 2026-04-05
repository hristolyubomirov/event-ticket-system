package com.example.event_ticket_system;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepo extends JpaRepository<Outbox, Long> {
    List<Outbox> findByProcessedFalse();
}
