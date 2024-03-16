package com.fitness.courses.http.coach.variable.service;

import java.util.List;

import com.fitness.courses.http.coach.variable.model.entity.CourseVariableEntity;

public interface CrudCourseVariableService
{
    List<CourseVariableEntity> findAllByCourseId(Long courseId);
}
