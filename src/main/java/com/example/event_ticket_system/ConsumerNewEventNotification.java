package com.example.event_ticket_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.util.List;
@Service
public class ConsumerNewEventNotification {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerNewEventNotification.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics="new-event-notify", groupId="kafka-user-preference")
    public void notifyUserPreference(@Payload EventsRecord eventsRecord) {

        try {

            logger.info(eventsRecord.toString());
            String category_kafka =eventsRecord.eventCategory();
            String name_kafka = eventsRecord.eventName();
            Long eventId_kafka = eventsRecord.eventId();
            System.out.println(category_kafka + name_kafka + eventId_kafka);
            logger.info(category_kafka + name_kafka + eventId_kafka);
            Double price_kafka = eventsRecord.price();
            String location_kafka = eventsRecord.eventCategory();
            System.out.println(category_kafka + name_kafka + " NOW IS DONE");

            logger.info(category_kafka + name_kafka + " NOW IS DONE");
            List<Users> usersPrefer = userRepository.findByPrefCategory(category_kafka);

            if (usersPrefer.isEmpty()) {
                System.out.println("No users prefer " + category_kafka);
                logger.info("No users prefer " + category_kafka);

            } else {
                for (Users u : usersPrefer) {
                    String buyLink = "http://localhost:8081/booking-ticket?eventId=" + eventId_kafka + "&userId=" + u.getUserId();


                    String bodyEmail = "<html>" +
                            "<body>" +
                            "<h2>Check this out!</h2>" +
                            "<p>We found a new <b>" + category_kafka + "</b>.</p>" + "<br/>" +
                            "<div>" +
                            "<h2>Name: " + name_kafka + "</h2>"
                            + "<h2>Price: €" + price_kafka + "</h2>"
                            + "<h2>Location: " + location_kafka + "</h2>" +
                            "<a href='" + buyLink + "'>Buy ticket here</a>" +
                            "</div>" +
                            "<br/>" + "<br/>" +
                            "</body>" +
                            "</html>";

                    //String bodyEmail = "Check this out - " + category_kafka + "." + "Name of the event" + name_kafka ;
                    String subject = "New event for you! Category: " + category_kafka;
                    emailService.sendEmail(u.getEmail(), subject, bodyEmail);
                    logger.info("Preference event sent!");
                    System.out.println("Preference event sent!");
                }


            }
    }catch(Exception e){
            logger.error("Failed kafka" + eventsRecord.toString() + " " + e);
        }
    }
}
