package com.example;

import com.example.dao.CustomerDAO;
import com.example.dao.OrderDAO;
import com.example.entities.Customer;
import com.example.entities.Order;
import com.example.jms.SimpleMockProducer;
import com.example.kafka.CustomerClient;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Singleton
public class Facade {
    private static final Logger log = LoggerFactory.getLogger(Facade.class);

    private  CustomerDAO dao;

    private OrderDAO orderdao;

    private CustomerClient kafkaClient;

    private SimpleMockProducer jmsClient;

    SimpleMockProducer producer ;

    public Facade(CustomerDAO dao,
                  CustomerClient kafkaClient,
                  SimpleMockProducer jmsClient,
                  OrderDAO orderDao,
                  SimpleMockProducer producer) {
        this.dao = dao;
        this.kafkaClient = kafkaClient;
        this.jmsClient = jmsClient;
        this.orderdao = orderDao;
        this.producer = producer;
    }
    public Iterable<Customer> getAllCustomers() {
        return dao.findAll();
    }
    public Customer getCustomerById(Long id) {
        return dao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    public Customer createCustomer(Customer customer) throws InterruptedException {
        var x =  dao.save(customer);
        kafkaClient.sendCustomer(customer).subscribe();
        return x;
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = dao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        return dao.update(existing);
    }
    public HttpResponse<Void> deleteCustomer(Long id) {
        dao.deleteById(id);
        return HttpResponse.noContent();
    }
    public Iterable<Order> getAllOrders() {
        return orderdao.findAll();
    }
    public Order getOrderById(Long id) {
        return orderdao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
    }
    public Order updateOrder(int id, @Body Order order) {
        Order existing = orderdao.findById((long) id)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
        existing.setDescription(order.getDescription());
        existing.setPrice(order.getPrice());
        return orderdao.update(existing);
    }
    public HttpResponse<Void> deleteOrder(Long id) {
        dao.deleteById(id);
        return HttpResponse.noContent();
    }
    public Order createOrder(@Body Order order) {
        var x = orderdao.save(order);
        producer.send(x.toString());
        return x;
    }

}
