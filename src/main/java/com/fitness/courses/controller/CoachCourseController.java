package com.fitness.courses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.global.exceptions.ResponseErrorException;
import com.fitness.courses.http.coach.card.dto.NewCardDto;
import com.fitness.courses.http.coach.course.dto.NewCourseDto;
import com.fitness.courses.http.coach.course.service.RestCourseService;

@RestController
@RequestMapping(path = "/coach")
public class CoachCourseController
{
    private final RestCourseService restCourseService;

    @Autowired
    public CoachCourseController(RestCourseService restCourseService)
    {
        this.restCourseService = restCourseService;
    }

    @PostMapping(value = "/course/create", consumes = "application/json")
    public ResponseEntity<?> createCourse(@RequestBody NewCourseDto newCourseDto)
    {
        try
        {
            restCourseService.createCourse(newCourseDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PostMapping(value = "/card/create", consumes = "application/json")
    public ResponseEntity<?> createCard(@RequestBody NewCardDto newCardDto)
    {
        try
        {

            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }
}
