package com.fitness.courses.http.coach.course.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.model.info.CourseEntityWithStudentsCount;

public interface CrudCourseEntityService
{
    void deleteById(@NotNull Long courseId);

    CourseEntity save(@NotNull CourseEntity courseEntity);

    CourseEntity update(@NotNull CourseEntity courseEntity);

    Optional<CourseEntity> findById(@NotNull Long id);

    CourseEntity findByIdOrThrow(@NotNull Long id) throws NotFoundException;

    List<CourseEntity> findByAuthorId(@NotNull Long authorId);

    List<CourseEntity> findAllSortByTimeCreatedDesc(@NotNull Integer offset, @NotNull Integer limit);

    List<CourseEntityWithStudentsCount> findAllSortByStudentsCount(@NotNull Integer offset, @NotNull Integer limit);

    List<CourseEntity> findAllByKeyword(@NotNull String keyword, @NotNull Integer offset, @NotNull Integer limit);
}
