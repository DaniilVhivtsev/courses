package com.fitness.courses.http.coach.course.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

public interface CrudCourseEntityService
{
    CourseEntity save(@NotNull CourseEntity courseEntity);

    CourseEntity update(@NotNull CourseEntity courseEntity);

    Optional<CourseEntity> findById(@NotNull Long id);

    CourseEntity findByIdOrThrow(@NotNull Long id) throws NotFoundException;

    List<CourseEntity> findByAuthorId(@NotNull Long authorId);
}
