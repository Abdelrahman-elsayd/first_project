package com.example.services;

import com.example.entities.Customer;
import com.example.Facade;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Singleton;

@Singleton
public class CustomerService {

    private Facade facade;

    public CustomerService(Facade facade) {
        this.facade = facade;
    }


    public Iterable<Customer> getAll() {return facade.getAllCustomers();}

    public Customer getById(Long id) {return facade.getCustomerById(id);}

    public Customer create(Customer customer) throws InterruptedException {return facade.createCustomer(customer);}

    public Customer update(Long id, Customer customer) {return facade.updateCustomer(id,customer);}
    public HttpResponse<Void> delete(Long id) {return facade.deleteCustomer(id);}
}
