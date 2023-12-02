package com.fitness.courses.http.coach.course.service;

import org.springframework.lang.NonNull;

import com.fitness.courses.http.coach.course.model.CourseEntity;

public interface CourseService
{
    void createCourse(@NonNull CourseEntity newCourseEntity);
}
