package com.fitness.courses.http.coach.course.content.service.module;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

public interface ModuleValidator
{
    void validateModuleTitle(String title) throws ValidationException;

    void validateModuleDescription(String description) throws ValidationException;

    void validateModuleBelongsToCourse(Long courseId, Long moduleId) throws ValidationException;

    void validateExist(@NotNull Long id) throws ValidationException;

    void validateSerialNumber(@NotNull CourseEntity courseEntity, @NotNull Integer serialNumber)
            throws ValidationException;

    void validateNoLessonsInModule(@NotNull Long moduleId) throws ValidationException;
}
