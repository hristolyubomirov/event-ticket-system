package com.example.event_ticket_system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface EventsRepo extends JpaRepository<Events,Long> , JpaSpecificationExecutor<Events> {

}
