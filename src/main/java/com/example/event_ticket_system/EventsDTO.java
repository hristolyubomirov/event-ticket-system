package com.example.event_ticket_system;

import com.jayway.jsonpath.internal.function.numeric.Min;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.time.LocalDateTime;


@Data
public class EventsDTO {
    @NotBlank
    private String eventName;
    private String category;
    private String location;
    private LocalDateTime eventDate;
    public Double price;
    public long ticketsCount;
}
