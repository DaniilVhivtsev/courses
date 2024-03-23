package com.fitness.courses.http.massenger.service.message;

import com.fitness.courses.global.exceptions.ValidationException;

public interface MessageValidator
{
    void validateTextMessage(String textMessage) throws ValidationException;
}
