package org.example.KafkaBatchListenerExample.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Data
public class NotificationsHistory {

    private Map<String, String> notifications = new HashMap<>();

    public AtomicBoolean isEmpty() {
        return new AtomicBoolean(notifications.isEmpty());
    }

    public void save(String id, String message) {
        notifications.put(id, message);
    }
}
