package org.example.KafkaBatchListenerExample.consumer;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.header.Header;
import org.example.KafkaBatchListenerExample.service.NotificationsHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Service
@Log4j2
public class BatchListener {

    @Autowired
    public NotificationsHistory notificationsHistory;

    @KafkaListener(
            topics = "${application.kafka-topics}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(ConsumerRecord<UUID, String> n) {

        log.info("START PROCESSING BATCH");
        Iterable<Header> traceparent = n.headers().headers("traceparent");
        Iterator<Header> iterator = traceparent.iterator();
        String str = "blank";
        if (iterator.hasNext()) {
            Header next = iterator.next();
            str = new String(next.value(), StandardCharsets.UTF_8);
        }

        log.info("Start processing notification with id: {}, trace: {}", n.key().toString(), str);
        notificationsHistory.save(n.key(), n.value());
        log.info("End processing notification with id: {}, trace: {}", n.key().toString(), str);
        log.info("END PROCESSING BATCH");
    }
}
