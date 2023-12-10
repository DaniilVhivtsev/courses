package com.fitness.courses.http.coach.course.content.service.lesson;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;

public interface CrudLessonEntityService
{
    @Transactional
    LessonEntity save(@NotNull LessonEntity entity);

    @Transactional
    LessonEntity update(@NotNull LessonEntity entity);

    @Transactional(readOnly = true)
    Optional<LessonEntity> findById(@NotNull Long id);

    @Transactional(readOnly = true)
    LessonEntity findByIdOrThrow(@NotNull Long id) throws NotFoundException;

    @Transactional(readOnly = true)
    List<LessonEntity> findAllByModuleIdAndSortAscBySerialNumber(@NotNull Long moduleId);
}
