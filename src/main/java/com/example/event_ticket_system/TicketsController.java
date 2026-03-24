package com.example.event_ticket_system;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketsController {

    @Autowired
    private TicketsRepo ticketsRepo;

    @PostMapping("/create-ticket")
    public void createTicket(@RequestBody Tickets ticket){
        ticketsRepo.save(ticket);

    }
}
