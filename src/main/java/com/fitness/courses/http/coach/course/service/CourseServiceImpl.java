package com.fitness.courses.http.coach.course.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.coach.course.model.CourseEntity;

@Service
public class CourseServiceImpl implements CourseService
{
    private final CrudCourseEntityService crudCourseEntityService;
    private final AuthService authService;

    @Autowired
    public CourseServiceImpl(CrudCourseEntityService crudCourseEntityService,
            AuthService authService)
    {
        this.crudCourseEntityService = crudCourseEntityService;
        this.authService = authService;
    }

    @Override
    public void createCourse(@NonNull CourseEntity newCourseEntity)
    {
        newCourseEntity.setAuthor(authService.getCurrentUserOrThrow());
        newCourseEntity.setDateTimeCreated(LocalDateTime.now(ZoneId.systemDefault()));
        crudCourseEntityService.save(newCourseEntity);
    }
}
