package com.fitness.courses.http.coach.course.content.service.module;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.model.info.ModuleWithLessonsInfo;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

public interface ModuleService
{
    List<ModuleEntity> findAllByCourseAndSortAscBySerialNumber(@NotNull CourseEntity course);

    List<ModuleWithLessonsInfo> findAllModulesWithLessonsByCourse(@NotNull CourseEntity course);
}
