package com.example.event_ticket_system;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.events.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Validated
public class EventController {


    private final EventsService eventService;
    public EventController(EventsService eventService){
        this.eventService = eventService;
    }

@PostMapping("/events-create")
@PreAuthorize("hasRole('ADMIN')")
    public Events createEvent(@Valid @RequestBody EventsDTO dto){

    return eventService.createNewEvent(dto);
}

@PatchMapping("/events-update/{eventId}")
@PreAuthorize("hasRole('ADMIN')")
    public Events update(@NotNull @Positive @PathVariable Long eventId, @Valid @RequestBody EventsDTO dto){
        return eventService.updateEvent(eventId,dto);
}

@DeleteMapping("/events-delete/{eventId}")
@PreAuthorize("hasRole('ADMIN')")
    public void delete(@NotNull @Positive @PathVariable Long eventId){
    eventService.deleteEvent(eventId);
}

@GetMapping("/events/all")
@PreAuthorize("hasRole('ADMIN')")
    public List<Events> getAllEvents(){
    return eventService.getAllEvents();
}

@GetMapping("/events/{eventId}")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Events getEvent(@Valid @PathVariable Long eventId){
        return eventService.getEvent(eventId);
}

@GetMapping("/events/search")
    public List<Events> filterEvents(@ModelAttribute EventsFilterDTO filter){
    return eventService.filterEventsBy(filter);
}

}
