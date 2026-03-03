import containers.KafkaTestContainer;
import org.example.KafkaBatchListenerExample.KafkaBatchListenerExample;
import org.example.KafkaBatchListenerExample.service.NotificationsHistory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(classes = KafkaBatchListenerExample.class)
@ExtendWith(SpringExtension.class)
public class BatchListenerIntTest extends KafkaTestContainer {

    @Autowired
    private KafkaTemplate<UUID, String> kafkaTemplate;
    @Autowired
    public NotificationsHistory notificationsHistory;
    @Value("${application.kafka-topics}")
    private String topic;

    @Test
    public void simpleTest() {
        Assertions.assertTrue(kafkaContainer.isRunning());
        kafkaSendMessages();
        await().pollInterval(Duration.ofSeconds(3))
                .atMost(10, SECONDS)
                .until(() -> notificationsHistory.getSize().equals(10));
    }

    private void kafkaSendMessages() {
        IntStream.range(0, 10)
                .forEach(i -> kafkaTemplate.send(topic, UUID.randomUUID(), "Simple test message"));
    }
}
