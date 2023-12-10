package com.fitness.courses.http.coach.course.content.service.lesson;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.ValidationException;

public interface LessonValidator
{
    void validateTitle(String title) throws ValidationException;

    void validateExist(@NotNull Long id) throws ValidationException;
}
