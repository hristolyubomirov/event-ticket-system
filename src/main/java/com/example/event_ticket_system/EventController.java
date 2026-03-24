package com.example.event_ticket_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @Autowired
    private EventsRepo eventsRepo;

    @PostMapping("/create-event")
    public void createEvent(@RequestBody Events event){
        eventsRepo.save(event);
    }
}
