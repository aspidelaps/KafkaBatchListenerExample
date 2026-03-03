package org.example.KafkaBatchListenerExample.consumer;

import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.example.KafkaBatchListenerExample.service.NotificationsHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BatchListener {

    @Autowired
    public NotificationsHistory notificationsHistory;

    @KafkaListener(
            topics = "${application.kafka-topics}",
            groupId = "${spring.kafka.consumer.group-id}",
            batch = "true"
    )
    public void consume(ConsumerRecords<UUID, String> notifications) {
        for (val n: notifications) {
            notificationsHistory.save(n.key(), n.value());
        }
    }
}
