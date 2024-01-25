package com.fitness.courses.http.coach.card.service;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.ValidationException;

public interface CardValidator
{
    void validateCardExist(@NotNull Long id) throws ValidationException;

    void validateCurrentUserHasPermission(@NotNull Long cardId) throws ValidationException;
}
