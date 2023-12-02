package com.fitness.courses.http.coach.course.service;

import org.springframework.lang.NonNull;

import com.fitness.courses.http.coach.course.dto.NewCourseDto;

public interface RestCourseService
{
    void createCourse(@NonNull NewCourseDto newCourseDto);
}
