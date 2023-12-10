package com.fitness.courses.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.http.coach.course.model.dto.RecommendedCourseDto;

@RestController
@RequestMapping("/public/course")
public class CatalogController
{
    @GetMapping("/new/all")
    public ResponseEntity<List<RecommendedCourseDto>> getNewCourses(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit)
    {
        return null;
    }

    @GetMapping("/popular/all")
    public ResponseEntity<List<RecommendedCourseDto>> getPopularCourses(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit)
    {
        return null;
    }

    @GetMapping("/searchBy")
    public ResponseEntity<List<RecommendedCourseDto>> getCoursesBySearchValue(
            @RequestParam("searchValue") String searchValue,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit)
    {
        return null;
    }
}
