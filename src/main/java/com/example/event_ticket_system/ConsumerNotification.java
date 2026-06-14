package com.example.event_ticket_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.Duration;

@Service
public class ConsumerNotification {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerNotification.class);
    private final StringRedisTemplate redisTemplate;
    public ConsumerNotification(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics="booking-notify", groupId="kafka-consume")
    public void consume(@Payload BookingRecord bookingRecord){
        Long bookingId = bookingRecord.bookingId();
        String redisKey = "notified:booking:" + bookingId;

        Boolean sent = redisTemplate.hasKey(redisKey);
        if(sent){
            logger.info("Already sent!");
            return;
        }

        logger.info("Sending...");
        redisTemplate.opsForValue().set(redisKey,"sent", Duration.ofHours(1));
    }


}
