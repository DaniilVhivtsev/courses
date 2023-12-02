package com.fitness.courses.http.coach.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.coach.course.dto.NewCourseDto;
import com.fitness.courses.http.coach.course.mapper.CourseMapper;

@Service
public class RestCourseServiceImpl implements RestCourseService
{
    private final CourseService courseService;
    private final CourseValidator courseValidator;

    @Autowired
    public RestCourseServiceImpl(
            CourseService courseService,
            CourseValidator courseValidator)
    {
        this.courseService = courseService;
        this.courseValidator = courseValidator;
    }

    @Override
    public void createCourse(@NonNull NewCourseDto newCourseDto)
    {
        courseValidator.validateCourseTitle(newCourseDto.getTitle());

        courseService.createCourse(CourseMapper.toEntity(newCourseDto));
    }
}
