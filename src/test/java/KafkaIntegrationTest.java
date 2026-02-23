import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class KafkaIntegrationTest {

    // Define the Kafka container using a specific Docker image
    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("apache/kafka-native:3.8.0")
    );

    // Dynamically set the Kafka bootstrap servers property in the Spring context
    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }
}