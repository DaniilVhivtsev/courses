package com.fitness.courses.http.coach.course.service;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.ValidationException;

public interface CourseValidator
{
    void validateCourseTitle(String title);

    void validateCourseExist(@NotNull Long id) throws ValidationException;

    void validateCurrentUserHasPermission(@NotNull Long courseId) throws ValidationException;
}
