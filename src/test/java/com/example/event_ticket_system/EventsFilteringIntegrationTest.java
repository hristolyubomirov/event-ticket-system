package com.example.event_ticket_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@TestPropertySource(locations="classpath:application-test.properties")
@ActiveProfiles("test")
@Transactional
class EventsFilteringIntegrationTest {

    @BeforeEach
    void cleanUp() {
        eventsRepo.deleteAll();
        eventsRepo.flush();
    }
    @MockitoBean
    private JavaMailSender javaMailSender;

    @Autowired
    private EventsService eventsService;

    @Autowired
    private EventsRepo eventsRepo;

    @Test
    void testFilteringByPriceAndCategory() {
        Events ev1 = new Events();
        ev1.setEventName("Wall-E"); ev1.setCategory("Movie"); ev1.setPrice(20.0); ev1.setEventDate(LocalDate.now()); ev1.setTicketsCount(100L); ev1.setLocation("Sofia");

        Events ev2 = new Events();
        ev2.setEventName("Spider-man"); ev2.setCategory("Movie"); ev2.setPrice(100.0); ev2.setEventDate(LocalDate.now());  ev2.setTicketsCount(100L); ev2.setLocation("Sofia");

        Events ev3 = new Events();
        ev3.setEventName("Pop Music Festival"); ev3.setCategory("Concert"); ev3.setPrice(50.0); ev3.setEventDate(LocalDate.now()); ev3.setTicketsCount(100L); ev3.setLocation("Sofia");

        eventsRepo.saveAll(List.of(ev1, ev2, ev3));
        EventsFilterDTO filter = new EventsFilterDTO();
        filter.setPriceFrom(40.0);
        filter.setCategory("Movie");

        List<Events> result = eventsService.filterEventsBy(filter);

        assertEquals(1, result.size(), "Only one event");
        assertEquals("Spider-man", result.getFirst().getEventName());    }
}