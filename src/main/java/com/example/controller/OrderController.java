package com.example.controller;
import com.example.entities.Order;
import com.example.services.OrderService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller("/orders")
public class OrderController {
    @Inject
    OrderService orderService;
    @Get("/")
    public Iterable<Order> getAll() {return orderService.getAll();}

    @Get("/{id}")
    public Order getById(Long id) {return orderService.getById(id);}

    @Post("/")
    public Order create(@Body Order order) {
        return orderService.create(order);
    }

    @Put("/{id}")
    public Order update(int id, @Body Order order) {return orderService.update(id, order);}

    @Delete("/{id}")
    public HttpResponse<Void> delete(Long id) {return orderService.delete(id);}

}
