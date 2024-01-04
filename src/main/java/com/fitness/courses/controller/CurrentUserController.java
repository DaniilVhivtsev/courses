package com.fitness.courses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.global.exceptions.ResponseErrorException;
import com.fitness.courses.http.student.service.RestStudentCoursesService;
import com.fitness.courses.http.user.dto.GeneralInfo;
import com.fitness.courses.http.user.dto.UserCurrentCourseInfo;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/user")
public class CurrentUserController
{
    private final RestStudentCoursesService restStudentCoursesService;

    @Autowired
    public CurrentUserController(
            RestStudentCoursesService restStudentCoursesService)
    {
        this.restStudentCoursesService = restStudentCoursesService;
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/currentCourses")
    public ResponseEntity<?> getUserCurrentCoursesInfo()
    {
        try
        {
            return new ResponseEntity<List<UserCurrentCourseInfo>>(
                    restStudentCoursesService.getStudentCurrentCoursesInfo(),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/generalInfo")
    public ResponseEntity<GeneralInfo> getGeneralInfoForCurrentUser()
    {
        return null;
    }
}
