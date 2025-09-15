package com.example;

import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/books")
public class BookController {

    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @Post
    public Book save(@Body Book book) {
        return repository.save(book);
    }

    @Get
    public List<Book> findAll() {
        return repository.findAll();
    }
}
