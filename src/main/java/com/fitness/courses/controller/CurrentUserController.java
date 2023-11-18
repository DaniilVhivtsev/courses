package com.fitness.courses.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.http.user.dto.GeneralInfo;
import com.fitness.courses.http.user.dto.UserCurrentCourseInfo;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/user")
public class CurrentUserController
{
    @SecurityRequirement(name = "JWT")
    @GetMapping("/currentCourse")
    public ResponseEntity<List<UserCurrentCourseInfo>> getUserCurrentCourseInfo()
    {
        return null;
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/generalInfo")
    public ResponseEntity<GeneralInfo> getGeneralInfoForCurrentUser()
    {
        return null;
    }
}
