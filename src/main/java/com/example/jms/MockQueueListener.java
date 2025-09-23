package com.example.jms;

import io.micronaut.jms.annotations.JMSListener;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedDeque;

@JMSListener("activeMqConnectionFactory")
public class MockQueueListener {

    public static ConcurrentLinkedDeque<String> receivedOrders = new ConcurrentLinkedDeque<>();
    private static final Logger log = LoggerFactory.getLogger(MockQueueListener.class);
    @Queue("orders")
    public void receive(@MessageBody String body) {
        try {
            receivedOrders.add(body);
            log.info("The order created is {}  ", body);
        } catch (Exception e) {
            log.error("Error processing message {}", e);
        }
    }
}
