package com.fitness.courses.http.auth.service;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.ConflictException;
import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.auth.dto.LoginRequest;

public interface AuthValidationService
{
    void validateUserLoginCredentials(@NotNull LoginRequest loginRequest) throws ValidationException;

    void validateUserExistByEmail(String email) throws ValidationException;

    void validateUserIsNotExistByEmail(String email) throws ConflictException;

    void validateUserEmailIsConfirmed(String email) throws ValidationException;
}
