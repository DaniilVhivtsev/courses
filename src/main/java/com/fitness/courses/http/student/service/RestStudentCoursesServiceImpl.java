package com.fitness.courses.http.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.model.entity.AdmissionToCourseBidEntity;
import com.fitness.courses.http.user.model.User;

@Service
public class RestStudentCoursesServiceImpl implements RestStudentCoursesService
{
    private final AuthService authService;
    private final AdmissionToCourseBidService admissionToCourseBidService;

    @Autowired
    public RestStudentCoursesServiceImpl(AuthService authService,
            AdmissionToCourseBidService admissionToCourseBidService)
    {
        this.authService = authService;
        this.admissionToCourseBidService = admissionToCourseBidService;
    }
}
