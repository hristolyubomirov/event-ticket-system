package com.example.event_ticket_system;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventsFilterDTO {
    private String eventName;
    private String category;
    private String location;
    private LocalDate start;
    private LocalDate end;
    private Double priceFrom;
    private Double priceTo;



}

