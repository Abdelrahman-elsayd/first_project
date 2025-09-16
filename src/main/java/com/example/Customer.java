package com.example;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
@Serdeable
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
