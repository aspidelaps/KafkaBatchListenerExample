package org.example.KafkaBatchListenerExample.consumer;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.example.KafkaBatchListenerExample.service.NotificationsHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
public class BatchListener {

    @Autowired
    public NotificationsHistory notificationsHistory;

    @KafkaListener(
            topics = "${application.kafka-topics}",
            groupId = "${spring.kafka.consumer.group-id}",
            batch = "true"
    )
    public void consume(ConsumerRecords<UUID, String> notifications) {

        log.info("START PROCESSING BATCH");
        for (val n: notifications) {
            log.info("Start processing notification with id: {}", n.key().toString());
            notificationsHistory.save(n.key(), n.value());
            log.info("End processing notification with id: {}", n.key().toString());
        }
        log.info("END PROCESSING BATCH");
    }
}
