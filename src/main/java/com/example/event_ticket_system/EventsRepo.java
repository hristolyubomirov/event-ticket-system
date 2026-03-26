package com.example.event_ticket_system;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventsRepo extends JpaRepository<Events,Long> , JpaSpecificationExecutor<Events> {
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT e from Events e WHERE e.eventId= :eventId")
    Optional<Events> findByIdWithLock(@Param("eventId") Long eventId);
}
