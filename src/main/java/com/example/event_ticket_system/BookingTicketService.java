package com.example.event_ticket_system;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(BookingTicketService.class);

    @Autowired
    private BookingTicketRepo bookingTicketRepo;

    @Autowired
    private EventsRepo eventsRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

   // private final Map<Long, Semaphore> eventSemaphoresLocks = new ConcurrentHashMap<>();

    @Transactional
    public Long bookTicket(Long eventId, Long userId) {
        // Semaphore semaphore = eventSemaphoresLocks.computeIfAbsent(eventId,k -> new Semaphore(1));


            // semaphore.acquire();

            Events event = eventsRepo.findByIdWithLock(eventId).orElseThrow(() -> new RuntimeException("ID not found."));
            Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            if (event.getTicketsCount() <= 0) {
                logger.info("Tickets sold.");
                throw new RuntimeException(("Tickets sold."));

            }

            event.setTicketsCount(event.getTicketsCount() - 1);
            eventsRepo.save(event);


            BookingTicket bookingTicket = new BookingTicket();
            bookingTicket.setUser(user);
            bookingTicket.setEvent(event);
            bookingTicketRepo.save(bookingTicket);
            String kafkaMsg = "BookingId:" + bookingTicket.getBookingId() + ", " + userId + " booked a ticket for event " + eventId + " successfully!";
            logger.info(kafkaMsg);

            kafkaTemplate.send("booking-notify", kafkaMsg);
            return bookingTicket.getBookingId();
        }

        /*catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RuntimeException("Try again later.");
        }finally {
            //semaphore.release();

        */




    @Transactional
    public void cancelBooking(Long bookingId){
        BookingTicket bt = bookingTicketRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found."));
        Events event = bt.getEvent();
        event.setTicketsCount(event.getTicketsCount()+1);
        bookingTicketRepo.delete(bt);
        logger.info("Booking canceled successfully!");
    }

    public List<BookingTicket> getAllBookings(@RequestParam Long userId){
        if(!userRepository.existsById(userId)){
            throw new RuntimeException("User not found.");
        }
        return bookingTicketRepo.findByUser_UserId(userId);

    }



}
