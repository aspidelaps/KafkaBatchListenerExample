package org.example.KafkaBatchListenerExample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Useless class, used only for local testing, should get deleted.
 */
@RestController
public class TestRest {

    @Autowired
    public KafkaTemplate<UUID, String> kafkaTemplate;

    @PostMapping("/send")
    public void sendTestMessage() {
        Message<String> message = MessageBuilder
                .withPayload("Simple test message")
                .setHeader(KafkaHeaders.KEY, UUID.randomUUID()) // Sets Kafka record key
                .setHeader(KafkaHeaders.TOPIC, "kafka-test-batch-topic") // Sets destination topic
                .setHeader("traceparent", "00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01") // Custom String header
                .build();
        kafkaTemplate.setObservationEnabled(true);
        kafkaTemplate.send(message);
    }
}
