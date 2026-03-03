package org.example.KafkaBatchListenerExample.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Data
public class NotificationsHistory {

    private Map<UUID, String> notifications = new HashMap<>();
    private AtomicBoolean isEmpty = new AtomicBoolean(true);

    public AtomicBoolean isEmpty() {
        return isEmpty;
    }

    public void save(UUID id, String message) {
        notifications.put(id, message);
        if (isEmpty.get()) {
            isEmpty.set(false);
        }
    }
}
