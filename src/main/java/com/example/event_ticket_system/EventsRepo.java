package com.example.event_ticket_system;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepo extends JpaRepository<Events,Long> {

}
