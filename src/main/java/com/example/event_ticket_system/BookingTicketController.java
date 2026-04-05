package com.example.event_ticket_system;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.constraints.*;

@RestController
@Validated
public class BookingTicketController {

    @Autowired
    private BookingTicketService bookingTicketService;

    @Autowired
    private BookingTicketRepo bookingTicketRepo;





    @PostMapping("/booking-ticket")
    public ResponseEntity<String> bookingCreate(@RequestParam @NotNull @Positive Long eventId,@RequestParam @NotNull @Positive Long userId){
        return bookingTicketService.bookTicket(eventId,userId);
        //      Long bookingId = bookingTicketService.bookTicket(eventId,userId);
//
//        String msg = "Booking is successful!<br/><br/>" +
//                "Cancel <a href=\"http://localhost:8081/bookings/cancel?bookingId=" + bookingId + "\">here</a>";

        //return ResponseEntity.status(HttpStatus.CREATED).header("Content-Type", "text/html").body(msg);



    }

    @GetMapping("/bookings")
    public List<BookingTicket> getAllBookings(@RequestParam @NotNull @Positive Long userId){
      return bookingTicketService.getAllBookings(userId);
    }
    @DeleteMapping("/bookings/cancel")
    public ResponseEntity<String> cancelBooking(@RequestParam @NotNull @Positive Long bookingId){
        return bookingTicketService.cancelBooking(bookingId);
    }

}
