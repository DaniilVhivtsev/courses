package com.fitness.courses.http.coach.variable.service;

import java.util.List;

import com.fitness.courses.http.coach.course.model.dto.greeting.GreetingCourseVariableDto;

public interface CourseVariableValidator
{
    void updateCourseVariables(Long courseId, List<GreetingCourseVariableDto> variableDtoList);
}
