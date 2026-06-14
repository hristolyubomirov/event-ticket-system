package com.example.event_ticket_system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EventsServiceTest {

    @Mock
    private EventsRepo eventsRepo;

    @Mock
    private KafkaTemplate<String, EventsRecord> kafkaTemplate;

    @InjectMocks
    private EventsService eventsService;

    @Test
    void testCreateEvent() {
        EventsDTO dto = new EventsDTO();
        dto.setEventName("Test Event");
        dto.setPrice(10.1);
        dto.setLocation("Sofia");
        dto.setCategory("Music");
        dto.setTicketsCount(100L);

        Events event = new Events();

        when(eventsRepo.save(any())).thenReturn(event);

        Events result = eventsService.createNewEvent(dto);
        verify(eventsRepo).save(any());
        verify(kafkaTemplate).send(any(), any());
        assertNotNull(result);
    }
}