package com.fitness.courses.http.coach.course.content.service.stage;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;

public interface StageValidator
{
    void validateExist(@NotNull Long id) throws ValidationException;

    void validateStageBelongsToLesson(@NotNull Long lessonId, @NotNull Long stageId);

    void validateSerialNumber(@NotNull LessonEntity lessonEntity, @NotNull Integer serialNumber)
            throws ValidationException;
}
