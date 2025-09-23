package com.example.kafka;

import com.example.entities.Customer;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.apache.kafka.clients.producer.RecordMetadata;
import reactor.core.publisher.Mono;

@KafkaClient
public interface CustomerClient {
    @Topic("customers")
    Mono<RecordMetadata> sendCustomer(Customer customer);
}
