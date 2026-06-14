package com.example.event_ticket_system;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.apache.kafka.shaded.io.opentelemetry.proto.trace.v1.Status;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventsService {


    private final EventsRepo eventsRepo;
    private final KafkaTemplate<String,EventsRecord> kafkaTemplate;
    //public EventsService(EventsRepo eventsRepo,KafkaTemplate<String, EventsRecord> kafkaTemplate){
    public EventsService(EventsRepo eventsRepo,KafkaTemplate<String, EventsRecord> kafkaTemplate){
        this.eventsRepo = eventsRepo;
        this.kafkaTemplate = kafkaTemplate;
    }

//Map<Long,Events> allEvents = new HashMap<>();
@CacheEvict(value="events", allEntries = true)
    public Events createNewEvent(@NotBlank EventsDTO dto){
        Events event = new Events();
        event.setEventName(dto.getEventName());
        event.setPrice(dto.getPrice());
        event.setLocation(dto.getLocation());
        event.setCategory(dto.getCategory());
        event.setTicketsCount(dto.getTicketsCount());
        event.setEventDate(LocalDate.now());
        eventsRepo.save(event);
        //String kafkaMsg= String.format("Category:%s,Name:%s,EventId:%s,Price:%s,Location:%s",event.getCategory(),event.getEventName(),event.getEventId(),event.getPrice(),event.getLocation());

        EventsRecord eventsRecord = new EventsRecord(event.getCategory(),event.getEventName(),event.getEventId(),event.getPrice(),event.getLocation());

        kafkaTemplate.send("new-event-notify",eventsRecord);

        return event;
    }

//refactored
private Events findEventOrThrow(long eventId){
    return eventsRepo.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
}
@CacheEvict(value="events", allEntries = true)
public Events updateEvent(@NotNull @Positive long eventId, @NotNull EventsDTO dto){
    Events event = findEventOrThrow(eventId);
    applyUpdates(event,dto);
    return eventsRepo.save(event);
}

private void applyUpdates(@NonNull Events event, @NonNull EventsDTO dto){
    Optional.ofNullable(dto.getEventName()).ifPresent(event::setEventName);
    Optional.ofNullable(dto.getPrice()).ifPresent(event::setPrice);
    Optional.ofNullable(dto.getCategory()).ifPresent(event::setCategory);
    Optional.ofNullable((dto.getLocation())).ifPresent(event::setLocation);
    Optional.ofNullable(dto.getTicketsCount()).ifPresent(event::setTicketsCount);
}


    @CacheEvict(value="events", allEntries = true)
    public void deleteEvent(Long eventId){
        eventsRepo.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("ID not found."));
        eventsRepo.deleteById(eventId);
    }

    public List<Events> getAllEvents(){
        return eventsRepo.findAll();
    }

    public Events getEvent(Long eventId){
       return eventsRepo.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("ID not found."));

    }
//refactor

    @Cacheable(value="events" , key="{#filter}")
    public List<Events> filterEventsBy(@NotNull EventsFilterDTO filter) {

        Specification<Events> spec = (root, query, cb) -> cb.conjunction();

                spec = spec.and(byName(filter.getEventName()))
                .and(byCategory(filter.getCategory()))
                .and(byLocation(filter.getLocation()))
                .and(fromDate(filter.getStart()))
                .and(toDate(filter.getEnd()))
                .and(fromPrice(filter.getPriceFrom()))
                .and(toPrice(filter.getPriceTo()));

        return eventsRepo.findAll(spec,Sort.by(Sort.Direction.ASC,"eventDate"));
    }
public Specification<Events> byName(String eventName){
        return eventName == null ? (root, query, cb) -> cb.conjunction() :  (root,query,cb) ->
                cb.equal(cb.lower(root.get("eventName")),eventName.toLowerCase());
}


    private Specification<Events> byCategory(String category) {
        return category == null ? (root, query, cb) -> cb.conjunction() : (root, query, cb) ->
                cb.equal(cb.lower(root.get("category")), category.toLowerCase());
    }

    private Specification<Events> byLocation(String location) {
        return location == null ? (root, query, cb) -> cb.conjunction() : (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("eventDate"), location);
    }

    private Specification<Events> fromDate(LocalDate start) {
        return start == null ? (root, query, cb) -> cb.conjunction() : (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("location"), start);
    }

    private Specification<Events> toDate(LocalDate end) {
        return end == null ? (root, query, cb) -> cb.conjunction() : (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("location"), end);
    }

    private Specification<Events> fromPrice(Double priceFrom) {
        return priceFrom == null ? (root, query, cb) -> cb.conjunction() : (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("price"), priceFrom);
    }

    private Specification<Events> toPrice(Double priceTo) {
        return priceTo == null ? (root, query, cb) -> cb.conjunction() : (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("price"), priceTo);
    }

}
