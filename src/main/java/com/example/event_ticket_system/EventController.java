package com.example.event_ticket_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.events.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventsService eventService;

@PostMapping("/events-create")
    public void createEvent(@RequestBody EventsDTO dto){
    eventService.createNewEvent(dto);
}

@PatchMapping("/events-update/{eventId}")
    public void update(@PathVariable Long eventId, @RequestBody EventsDTO dto){
        eventService.updateEvent(eventId,dto);
}

@DeleteMapping("/events-delete/{eventId}")
    public void delete(@PathVariable Long eventId){
    eventService.deleteEvent(eventId);
}

@GetMapping("/events/all")
    public List<Events> getAllEvents(){
    return eventService.getAllEvents();
}

@GetMapping("/events/{eventId}")
    public Events getEvent(@PathVariable Long eventId){
        return eventService.getEvent(eventId);
}

@GetMapping("/events/search")
    public List<Events> filterEvents(
        @RequestParam(required = false)String eventName,
        @RequestParam(required = false)String category,
        @RequestParam(required = false)String location,
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy")LocalDate start,
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate end,
        @RequestParam(required = false)Double priceFrom,
        @RequestParam(required = false)Double priceTo
){
    return eventService.filterEventsBy(eventName,category,location,start,end,priceFrom,priceTo);
}

}
