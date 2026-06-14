package com.example.event_ticket_system;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.w3c.dom.events.Event;

@Service
public class BookingTicketService {

    private static final Logger logger = LoggerFactory.getLogger(BookingTicketService.class);

    private final BookingTicketRepo bookingTicketRepo;
    private final EventsRepo eventsRepo;
    private final UserRepository userRepository;
    private final OutboxRepo outboxRepo;
    private final ObjectMapper objectMapper;

    public BookingTicketService(BookingTicketRepo bookingTicketRepo, EventsRepo eventsRepo, UserRepository userRepository, OutboxRepo outboxRepo, ObjectMapper objectMapper){
        this.bookingTicketRepo = bookingTicketRepo;
        this.eventsRepo = eventsRepo;
        this.userRepository = userRepository;
        this.outboxRepo = outboxRepo;
        this.objectMapper = objectMapper;
    }

   // private final Map<Long, Semaphore> eventSemaphoresLocks = new ConcurrentHashMap<>();

    @Transactional
    public ResponseEntity<String> bookTicket(Long eventId, Long userId) {
        // Semaphore semaphore = eventSemaphoresLocks.computeIfAbsent(eventId,k -> new Semaphore(1));


            // semaphore.acquire();

            Events event = eventsRepo.findByIdWithLock(eventId).orElseThrow(() -> new ResourceNotFoundException("ID not found."));
            Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            if (event.getTicketsCount() <= 0) {
                logger.info("Tickets sold.");
                throw new BadRequestException(("Tickets sold."));

            }

            event.setTicketsCount(event.getTicketsCount() - 1);
            eventsRepo.save(event);


            BookingTicket bookingTicket = new BookingTicket();
            bookingTicket.setUser(user);
            bookingTicket.setEvent(event);
            bookingTicketRepo.save(bookingTicket);
            //String kafkaMsg = "BookingId:" + bookingTicket.getBookingId() + ", " + userId + " booked a ticket for event " + eventId + " successfully!";

try {
    BookingRecord bookingRecord = new BookingRecord(
            bookingTicket.getBookingId(),
            userId,
            eventId,
            LocalDate.now()
    );

    String jsonPayload = objectMapper.writeValueAsString(bookingRecord);
    logger.info(bookingRecord.toString());

    Outbox outb = new Outbox();
    outb.setTopic("booking-notify");
    outb.setPayload(jsonPayload);
    outboxRepo.save(outb);
}catch (JsonProcessingException e) {
        throw new RuntimeException("Failed to serialize booking record.",e);
}


            return ResponseEntity.status(HttpStatus.CREATED).body("Booking is successful!\n Booking ID: " + bookingTicket.getBookingId());
        }

        /*catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RuntimeException("Try again later.");
        }finally {
            //semaphore.release();

        */




    @Transactional
    public ResponseEntity<String> cancelBooking(Long bookingId){
        BookingTicket bt = bookingTicketRepo.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found."));
        Events event = bt.getEvent();
        event.setTicketsCount(event.getTicketsCount()+1);
        bookingTicketRepo.delete(bt);
        logger.info("Booking canceled successfully!");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Booking is canceled successfully!");
    }

    public List<BookingTicket> getAllBookings(@NotNull @Positive @RequestParam Long userId){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User not found.");
        }
        return bookingTicketRepo.findByUser_UserId(userId);

    }



}
