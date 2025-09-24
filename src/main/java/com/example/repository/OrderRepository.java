package com.example.repository;

import com.example.entities.Order;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
@Repository

public interface OrderRepository extends CrudRepository<Order, Long> {
}
