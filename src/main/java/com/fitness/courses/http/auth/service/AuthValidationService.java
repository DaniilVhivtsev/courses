package com.fitness.courses.http.auth.service;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.auth.dto.LoginRequestDto;

public interface AuthValidationService
{
    void validateUserLoginCredentials(@NotNull LoginRequestDto loginRequestDto) throws ValidationException;

    void validateUserExistByEmail(String email) throws ValidationException;

    void validateUserIsNotExistByEmail(String email) throws ValidationException;

    void validateUserEmailIsConfirmed(String email) throws ValidationException;
}
