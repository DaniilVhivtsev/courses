package com.fitness.courses.http.coach.course.model.info;

import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

public record CourseEntityWithStudentsCount(CourseEntity course, Integer studentsCount)
{
}
