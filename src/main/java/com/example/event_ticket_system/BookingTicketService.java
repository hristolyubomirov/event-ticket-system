package com.example.event_ticket_system;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

@Service
public class BookingTicketService {


    @Autowired
    private BookingTicketRepo bookingTicketRepo;

    @Autowired
    private EventsRepo eventsRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    private final Map<Long, Semaphore> eventSemaphoresLocks = new ConcurrentHashMap<>();

    @Transactional
    public void bookTicket(Long eventId, Long userId){
        Semaphore semaphore = eventSemaphoresLocks.computeIfAbsent(eventId,k -> new Semaphore(1));

        try{
            semaphore.acquire();

        Events event = eventsRepo.findById(eventId).orElseThrow(() -> new RuntimeException("ID not found."));
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if(event.getTicketsCount() < 0){
            throw new RuntimeException(("Tickets sold."));
        }

        event.setTicketsCount(event.getTicketsCount()-1);
        eventsRepo.save(event);


        BookingTicket bookingTicket = new BookingTicket();
        bookingTicket.setUser(user);
        bookingTicket.setEvent(event);
        String kafkaMsg = "BookingId:" + bookingTicket.getBookingId() + ", " +  userId+ " booked a ticket for event " + eventId+ " successfully!";

        bookingTicketRepo.save(bookingTicket);
        kafkaTemplate.send("booking-notify",kafkaMsg);

        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RuntimeException("Try again later.");
        }finally {
            semaphore.release();
        }
    }


    @Transactional
    public void cancelBooking(Long bookingId){
        BookingTicket bt = bookingTicketRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found."));
        Events event = bt.getEvent();
        event.setTicketsCount(event.getTicketsCount()+1);
        bookingTicketRepo.delete(bt);

    }

    public List<BookingTicket> getAllBookings(@RequestParam Long userId){
        if(!userRepository.existsById(userId)){
            throw new RuntimeException("User not found.");
        }
        return bookingTicketRepo.findByUser_UserId(userId);

    }



}
