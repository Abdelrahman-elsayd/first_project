package com.example;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class CustomerDAO {

    private final CustomerRepository repository;

    public CustomerDAO(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    public Iterable<Customer> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    @Transactional
    public Customer update(Customer customer) {
        return repository.update(customer); // ✅ attach and update
    }
}
