package com.fitness.courses.http.coach.course.content.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.content.model.entity.StageEntity;

public interface CrudStageEntityService
{
    @Transactional
    StageEntity save(@NotNull StageEntity entity);

    @Transactional
    StageEntity update(@NotNull StageEntity entity);

    @Transactional(readOnly = true)
    Optional<StageEntity> findById(@NotNull Long id);

    @Transactional(readOnly = true)
    StageEntity findByIdOrThrow(@NotNull Long id) throws NotFoundException;

    @Transactional(readOnly = true)
    List<StageEntity> findAllByLessonIdAndSortAscBySerialNumber(@NotNull Long lessonId);
}
