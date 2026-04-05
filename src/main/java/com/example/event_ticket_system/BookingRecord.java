package com.example.event_ticket_system;

import java.time.LocalDate;

public record BookingRecord(Long bookingId, Long userId,Long eventId, LocalDate timestamp) {
}
