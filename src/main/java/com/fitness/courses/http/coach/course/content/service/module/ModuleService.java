package com.fitness.courses.http.coach.course.content.service.module;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.course.content.model.dto.NewCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.UpdateCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.model.info.ModuleWithLessonsInfo;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

public interface ModuleService
{
    void add(CourseEntity courseEntity, NewCourseAuthorModuleDto newModuleDto);

    List<ModuleEntity> findAllByCourseAndSortAscBySerialNumber(@NotNull CourseEntity course);

    List<ModuleWithLessonsInfo> findAllModulesWithLessonsByCourse(@NotNull CourseEntity course);

    Optional<ModuleEntity> getOptional(@NotNull Long id);

    ModuleEntity getOrThrow(@NotNull Long id);

    void update(@NotNull CourseEntity courseEntityFromDb, @NotNull Long moduleId,
            @NotNull UpdateCourseAuthorModuleDto updateModuleDto);
}
