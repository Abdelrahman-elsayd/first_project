package com.example.dao;

import com.example.entities.Order;
import com.example.repository.OrderRepository;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;

import java.util.Optional;
@Singleton
public class OrderDAO {
    private final OrderRepository repository;

    public OrderDAO(OrderRepository repository) {
        this.repository = repository;
    }

    public Order save(Order order) {
        return repository.save(order);
    }

    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

    public Iterable<Order> findAll() {return repository.findAll();}


    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    @Transactional
    public Order update(Order order) {
        return repository.update(order);
    }
}
