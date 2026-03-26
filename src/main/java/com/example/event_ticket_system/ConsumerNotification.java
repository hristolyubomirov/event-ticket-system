package com.example.event_ticket_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.Duration;

@Service
public class ConsumerNotification {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerNotification.class);
    @Autowired
    private StringRedisTemplate redisTemplate;

    @KafkaListener(topics="booking-notify", groupId="kafka-consume")
    public void consume(String kafkaMsg){
        String bookingId = kafkaMsg.split(":")[1].split(",")[0];
        String redisKey = "notified:booking:" + bookingId;

        Boolean sent = redisTemplate.hasKey(redisKey);
        if(sent){
            System.out.println("Already sent!");
            logger.info("Already sent!");
            return;
        }

        System.out.println("Sending...");
        logger.info("Sending...");
        redisTemplate.opsForValue().set(redisKey,"sent", Duration.ofHours(1));
    }


}
