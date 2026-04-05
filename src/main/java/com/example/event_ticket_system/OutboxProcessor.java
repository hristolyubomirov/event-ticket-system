package com.example.event_ticket_system;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutboxProcessor {
    private static final Logger logger = LoggerFactory.getLogger(OutboxProcessor.class);

    @Autowired
    private OutboxRepo outboxRepo;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 2000)
    @Transactional
    public void processOutbox() {
        List<Outbox> Messages = outboxRepo.findByProcessedFalse();

        for (Outbox msg : Messages) {
            try {
                kafkaTemplate.send(msg.getTopic(), msg.getPayload()).get();
                msg.setProcessed(true);
                outboxRepo.save(msg);

            } catch (Exception e) {
                logger.error("Kafka is down -  {}: {}", msg.getId(), e.getMessage());
            }
        }
    }
}