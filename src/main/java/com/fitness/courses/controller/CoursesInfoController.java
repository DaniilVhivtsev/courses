package com.fitness.courses.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.http.coach.course.dto.RecommendedCourseDto;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/course/")
public class CoursesInfoController
{
    @SecurityRequirement(name = "JWT")
    @GetMapping("/recommended/all")
    public ResponseEntity<List<RecommendedCourseDto>> getRecommendedCoursesForCurrentUser(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit)
    {
        return null;
    }
}
