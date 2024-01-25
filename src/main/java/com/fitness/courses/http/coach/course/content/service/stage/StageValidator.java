package com.fitness.courses.http.coach.course.content.service.stage;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;

public interface StageValidator
{
    void validateExist(@NotNull Long id) throws ValidationException;

    void validateStageBelongsToLesson(@NotNull Long lessonId, @NotNull Long stageId);

    void validateStageBelongsToCourse(@NotNull Long courseId, @NotNull Long stageId);

    void validateSerialNumber(@NotNull LessonEntity lessonEntity, @NotNull Integer serialNumber)
            throws ValidationException;

    void validateStageContentExist(@NotNull Long stageId, @NotNull String stageContentId) throws ValidationException;

    void validateContentSerialNumber(@NotNull Long stageId, @NotNull Integer contentSerialNumber)
            throws ValidationException;

    void validateExerciseContentExist(@NotNull Long stageId, @NotNull String stageContentUuid,
            @NotNull String stageContentExerciseUuid) throws ValidationException;

    void validateExerciseSetContentExist(@NotNull Long stageId, @NotNull String stageContentUuid,
            @NotNull String stageContentExerciseUuid, @NotNull String stageExerciseSetContentUuid);
}
