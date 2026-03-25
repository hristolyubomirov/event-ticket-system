package com.example.event_ticket_system;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingTicketController {

    @Autowired
    private BookingTicketService bookingTicketService;

    @Autowired
    private BookingTicketRepo bookingTicketRepo;

    @PostMapping("/booking-ticket")
    public ResponseEntity<String> bookingCreate(@RequestParam Long eventId, @RequestParam Long userId){
        bookingTicketService.bookTicket(eventId,userId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Booking is successful!");

    }

    @GetMapping("/bookings")
    public List<BookingTicket> getAllBookings(@RequestParam Long userId){
      return bookingTicketService.getAllBookings(userId);
    }
    @DeleteMapping("/bookings/cancel")
    public ResponseEntity<String> cancelBooking(@RequestParam Long bookingId){
        bookingTicketService.cancelBooking(bookingId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Booking is canceled successfully!");
    }

}
