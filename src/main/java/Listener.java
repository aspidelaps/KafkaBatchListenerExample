import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.protocol.Message;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {

    @KafkaListener(
            topics = "${application.kafka-topics}",
            groupId = "${spring.kafka.consumer.group-id}",
            batch = "true"
    )
    public void listener(ConsumerRecords<String, Message> records) {
        for (ConsumerRecord<String, Message> rec : records) {
            Message value = rec.value();
            System.out.println(value);
        }
    }
}
