package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

@Controller("/customers")
public class CustomerController {

    @Inject
    private CustomerDAO dao;

    @Get("/")
    public Iterable<Customer> getAll() {
        return dao.findAll();
    }

    @Get("/{id}")
    public Customer getById(Long id) {
        return dao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    @Post("/")
    public Customer create(@Body Customer customer) {
        return dao.save(customer);
    }

    @Put("/{id}")
    public Customer update(Long id, @Body Customer customer) {
        Customer existing = dao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        return dao.update(existing);
    }

    @Delete("/{id}")
    public HttpResponse<Void> delete(Long id) {
        dao.deleteById(id);
        return HttpResponse.noContent();
    }
}
