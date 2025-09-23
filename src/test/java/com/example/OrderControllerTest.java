package com.example;

import com.example.entities.Customer;
import com.example.jms.MockQueueListener;
import com.example.jms.SimpleMockProducer;
import com.example.kafka.CustomerClient;
import com.example.kafka.CustomerListener;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@MicronautTest
@TestInstance(PER_CLASS)
public class OrderControllerTest {
    @Inject
    CustomerListener customerListener;
    @Inject
    CustomerClient customerClient;


    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testSendClientToKafka() throws Exception {
        Customer customer = Customer.builder()
                .email("abdo@email.com")
                .name("abdo")
                .build();

        customerClient.sendCustomer(customer).block();

        Customer received = null;
        long end = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < end && (received = customerListener.received.poll()) == null) {
            Thread.sleep(100);
        }

        assertNotNull(received, "Order was not received from Kafka");
        assertEquals(customer.getId(), received.getId());
        assertEquals(customer.getEmail(), received.getEmail());
        assertEquals(customer.getName(), received.getName());
    }
    @Inject
    SimpleMockProducer producer;
    @Test
    void testSendAndReceiveOrder() throws InterruptedException {
        String order = "Laptop";
        producer.send(order);


        int retries = 20;
        String received = null;
        while (retries-- > 0) {
            received = MockQueueListener.receivedOrders.poll();
            if (received != null) break;
            Thread.sleep(100);
        }

        assertNotNull(received, "Order was not received");
        assertEquals(order, received);
    }

}
