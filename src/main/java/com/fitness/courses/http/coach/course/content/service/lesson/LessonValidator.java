package com.fitness.courses.http.coach.course.content.service.lesson;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;

public interface LessonValidator
{
    void validateTitle(String title) throws ValidationException;

    void validateExist(@NotNull Long id) throws ValidationException;

    void validateLessonBelongsToModule(@NotNull Long moduleId, @NotNull Long lessonId) throws ValidationException;

    void validateSerialNumber(@NotNull ModuleEntity moduleEntity, @NotNull Integer serialNumber)
            throws ValidationException;

    void validateNoStagesInLesson(@NotNull Long lessonId) throws ValidationException;
}
