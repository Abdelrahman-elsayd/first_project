package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
public class GlobalExceptionHandler implements ExceptionHandler<IllegalArgumentException, HttpResponse<String>> {

    @Override
    public HttpResponse<String> handle(HttpRequest request, IllegalArgumentException exception) {
        return HttpResponse.badRequest(exception.getMessage());
    }
}
