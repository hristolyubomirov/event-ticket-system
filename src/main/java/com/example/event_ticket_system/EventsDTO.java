package com.example.event_ticket_system;

import com.jayway.jsonpath.internal.function.numeric.Min;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
public class EventsDTO {
    @NotBlank
    private String eventName;
    private String category;
    private String location;
    private LocalDateTime eventDate;
    public Double price;
    public Long ticketsCount;
}
