package org.example.KafkaBatchListenerExample.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.example.KafkaBatchListenerExample.service.NotificationsHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.UUID;

@Service
@Slf4j
public class BatchListener {

    @Autowired
    public NotificationsHistory notificationsHistory;

    @KafkaListener(
            topics = "${application.kafka-topics}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(ConsumerRecord<UUID, String> record) {

        log.info("START PROCESSING BATCH");

        Header traceHeader = record.headers().lastHeader("traceparent");
        if (traceHeader != null) {
            System.out.println("RAW HEADER FOUND ON BROKER: " + new String(traceHeader.value()));
        } else {
            System.out.println("NO TRACEPARENT HEADER ARRIVED IN KAFKA RECORD!");
        }

        log.info("Start processing notification with id: {}", record.key().toString());
        notificationsHistory.save(record.key(), record.value());
        log.info("End processing notification with id: {}", record.key().toString());

        log.info("END PROCESSING BATCH");
    }
}
