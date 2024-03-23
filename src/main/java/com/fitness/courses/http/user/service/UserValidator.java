package com.fitness.courses.http.user.service;

import com.fitness.courses.global.exceptions.NotFoundException;

public interface UserValidator
{
    void validateUserExist(Long userId) throws NotFoundException;
}
