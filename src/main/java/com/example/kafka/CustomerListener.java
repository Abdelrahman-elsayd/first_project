package com.example.kafka;
import com.example.entities.Customer;
import com.example.services.CustomerService;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedDeque;
import static io.micronaut.configuration.kafka.annotation.OffsetReset.EARLIEST;
@KafkaListener(groupId = "test-group",offsetReset = EARLIEST)
@Singleton
public class CustomerListener {
    public static final ConcurrentLinkedDeque<Customer> received = new ConcurrentLinkedDeque<>();
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    @Topic("customers")
    public void receive(Customer customer) {
        received.add(customer);
        log.info("Customer is created with id {} and with email {} ", customer.getId(), customer.getEmail());
    }

}