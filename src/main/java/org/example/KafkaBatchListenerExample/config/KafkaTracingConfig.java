package org.example.KafkaBatchListenerExample.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.UUID;

@Configuration
public class KafkaTracingConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<UUID, String> kafkaListenerContainerFactory(
            ConsumerFactory<UUID, String> consumerFactory,
            ObservationRegistry observationRegistry) {

        ConcurrentKafkaListenerContainerFactory<UUID, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        // Force observation mapping directly onto the container properties
        factory.getContainerProperties().setObservationEnabled(true); //

        return factory;
    }

    @Bean
    public KafkaTemplate<UUID, String> kafkaTemplate(
            ProducerFactory<UUID, String> producerFactory,
            ObservationRegistry observationRegistry) {

        KafkaTemplate<UUID, String> template = new KafkaTemplate<>(producerFactory);
        template.setObservationEnabled(true);
        template.setObservationRegistry(observationRegistry);
        return template;
    }
}
