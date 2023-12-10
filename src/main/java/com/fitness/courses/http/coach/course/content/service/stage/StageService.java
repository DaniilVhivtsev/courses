package com.fitness.courses.http.coach.course.content.service.stage;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;

public interface StageService
{
    void add(@NotNull LessonEntity lesson);

    List<StageEntity> findAllByLessonAndSortAscBySerialNumber(@NotNull LessonEntity lesson);

    Optional<StageEntity> getOptional(@NotNull Long id);

    StageEntity getOrThrow(@NotNull Long id);
}
