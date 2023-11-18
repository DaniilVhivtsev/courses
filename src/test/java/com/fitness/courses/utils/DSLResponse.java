package com.fitness.courses.utils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record DSLResponse<T>(@Null T value, @NotNull ResponseEntity<T> responseEntity)
{
    public int statusCode()
    {
        return responseEntity.getStatusCode().value();
    }

    public T getBodyIfStatusOk()
    {
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        return value;
    }
}
