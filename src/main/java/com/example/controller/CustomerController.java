package com.example.controller;

import com.example.entities.Customer;
import com.example.services.CustomerService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller("/customers")
public class CustomerController {
    @Inject
    private CustomerService customerService;

    @Get("/")
    public Iterable<Customer> getAll() {
        return customerService.getAll();
    }

    @Get("/{id}")
    public Customer getById(Long id) {return customerService.getById(id);}

    @Post("/")
    public Customer create(@Body Customer customer) throws InterruptedException {
        return customerService.create(customer);
    }

    @Put("/{id}")
    public Customer update(Long id, @Body Customer customer) {return customerService.update(id, customer);}

    @Delete("/{id}")
    public HttpResponse<Void> delete(Long id) {return customerService.delete(id);}
}
