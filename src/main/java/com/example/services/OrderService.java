package com.example.services;

import com.example.Facade;
import com.example.entities.Order;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import jakarta.inject.Singleton;

@Singleton
public class OrderService {

    Facade facade;
    public Iterable<Order> getAll() {return facade.getAllOrders();}
    public Order getById(Long id) {return facade.getOrderById(id);}
    public Order update(int id, @Body Order order) {return facade.updateOrder(id,order);}
    public HttpResponse<Void> delete(Long id) {return facade.deleteOrder(id);}
    public Order create(@Body Order order) {
        return facade.createOrder(order);
    }
}
