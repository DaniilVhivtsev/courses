package com.fitness.courses.http.coach.variable.service;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.course.model.dto.greeting.GreetingCourseVariableDto;
import com.fitness.courses.http.coach.variable.model.entity.CourseVariableEntity;

public interface CourseVariableService
{
    CourseVariableEntity findByIdOrThrow(Long courseVariableId);

    List<CourseVariableEntity> findAllByCourseId(Long courseId);

    Set<Long> findAllStageUuidsWithVariableInCourse(@NotNull Long courseId, @NotNull String variableCode);

    void update(@NotNull Long courseId, @NotNull List<GreetingCourseVariableDto> variableDtoList);
}
