import org.example.KafkaBatchListenerExample.KafkaBatchListenerExample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes =  KafkaBatchListenerExample.class)
@ExtendWith(SpringExtension.class)
public class KafkaBatchListenerExampleIntTest extends KafkaIntegrationTest {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void simpleTest() {
        kafkaTemplate.send("kafka-test-batch-topic", "Simple test message");
    }
}
