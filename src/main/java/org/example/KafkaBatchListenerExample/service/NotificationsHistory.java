package org.example.KafkaBatchListenerExample.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Data
public class NotificationsHistory {

    private Map<UUID, String> notifications = new HashMap<>();

    public Integer getSize() {
        return notifications.size();
    }

    public void save(UUID id, String message) {
        notifications.put(id, message);
    }
}
