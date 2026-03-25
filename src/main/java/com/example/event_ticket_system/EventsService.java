package com.example.event_ticket_system;

import org.apache.kafka.shaded.io.opentelemetry.proto.trace.v1.Status;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventsService {

    @Autowired
    private EventsRepo eventsRepo;

Map<Long,Events> allEvents = new HashMap<>();

    public void createNewEvent(@NonNull EventsDTO dto){
        Events event = new Events();
        event.setEventName(dto.getEventName());
        event.setPrice(dto.getPrice());
        event.setLocation(dto.getLocation());
        event.setCategory(dto.getCategory());
        event.setTicketsCount(dto.getTicketsCount());
        event.setEventDate(LocalDate.now());
        eventsRepo.save(event);
    }



    public void updateEvent(long eventId, @NonNull EventsDTO dto){
        Events event = eventsRepo.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        if(dto.getEventName() != null){
            event.setEventName((dto.getEventName()));
        }
        if(dto.getPrice() != 0){
            event.setPrice(dto.getPrice());
        }
        if(dto.getCategory() != null){
            event.setCategory(dto.getCategory());
        }
        if(dto.getLocation() != null){
            event.setLocation(dto.getLocation());
        }

        if(dto.getTicketsCount() != 0){
            event.setTicketsCount(dto.getTicketsCount());
        }


        eventsRepo.save(event);
    }


    public void deleteEvent(Long eventId){
        eventsRepo.findById(eventId).orElseThrow(() -> new RuntimeException("ID not found."));
        eventsRepo.deleteById(eventId);
    }

    public List<Events> getAllEvents(){
        return eventsRepo.findAll();
    }

    public Events getEvent(Long eventId){
       return eventsRepo.findById(eventId).orElseThrow(() -> new RuntimeException("ID not found."));

    }

    public List<Events> filterEventsBy(String eventName, String category, String location, LocalDate start, LocalDate end, Double priceFrom, Double priceTo) {

        //check
        Specification<Events> specEvents = Specification.where((root,query,cb) -> cb.conjunction());
        if(eventName != null){
            specEvents = specEvents.and((root,query,cb) -> cb.like(cb.lower(root.get("eventName")), "%" + eventName.toLowerCase() + "%"));
        }
        if(category != null){
            specEvents = specEvents.and((root,query,cb) -> cb.like(cb.lower(root.get("category")), "%" + category.toLowerCase() + "%"));
        }

        if(location != null && !location.trim().isEmpty()){
            specEvents = specEvents.and((root,query,cb) -> cb.equal(cb.lower(root.get("location")),location.toLowerCase()));
        }

        if(start != null){
            specEvents = specEvents.and((root,query,cb) -> cb.greaterThanOrEqualTo(root.get("eventDate"),start));
        }


        if(end != null){
            specEvents = specEvents.and((root,query,cb) -> cb.lessThanOrEqualTo(root.get("eventDate"),end));
        }

        if(priceFrom != null) {
            specEvents = specEvents.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), priceFrom));
        }
        if(priceTo != null) {
            specEvents = specEvents.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), priceTo));
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "eventDate");
        return eventsRepo.findAll(specEvents,sort);
    }
}
