package com.example.jms;


import io.micronaut.http.annotation.Body;
import io.micronaut.jms.annotations.JMSProducer;
import io.micronaut.jms.annotations.Queue;
import io.micronaut.messaging.annotation.MessageBody;

@JMSProducer("activeMqConnectionFactory")
public interface SimpleMockProducer {
    @Queue("orders")
    void send(@MessageBody String body);
}
