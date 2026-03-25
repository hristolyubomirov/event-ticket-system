package com.example.event_ticket_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.Duration;

@Service
public class ConsumerNotification {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @KafkaListener(topics="booking-notify")
    public void consume(String kafkaMsg){
        String bookingId = kafkaMsg.split(":")[1].split(",")[0];
        String redisKey = "notified:booking:" + bookingId;

        Boolean sent = redisTemplate.hasKey(redisKey);
        if(sent){
            System.out.println("Already sent!");
            return;
        }

        System.out.println("Sending...");

        redisTemplate.opsForValue().set(redisKey,"sent", Duration.ofHours(1));
    }

}
