package com.fitness.courses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.global.exceptions.ResponseErrorException;
import com.fitness.courses.http.student.model.dto.stage.StageContentInfoDto;
import com.fitness.courses.http.student.service.RestStudentCoursesService;

@RestController
@RequestMapping(path = "/student")
public class StudentCourseController
{
    private final RestStudentCoursesService restStudentCoursesService;

    @Autowired
    public StudentCourseController(
            RestStudentCoursesService restStudentCoursesService)
    {
        this.restStudentCoursesService = restStudentCoursesService;
    }

    @GetMapping(value = "/course/{courseId}/stage/{stageId}/content")
    public ResponseEntity<?> getCourseStageContent(@PathVariable Long courseId, @PathVariable Long stageId)
    {
        try
        {
            return new ResponseEntity<StageContentInfoDto>(
                    restStudentCoursesService.getStageContent(courseId, stageId),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PostMapping(value = "/course/{courseId}/bid/registration")
    public ResponseEntity<?> createBidRegistrationForTheCourse(@PathVariable Long courseId)
    {
        try
        {
            restStudentCoursesService.createBidRegistrationForTheCourse(courseId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PostMapping(value = "/course/{courseId}/stage/{stageId}/content/set/{setId}/complete")
    public ResponseEntity<?> completeSet(@PathVariable Long courseId, @PathVariable Long stageId,
            @PathVariable String setId)
    {
        try
        {
            restStudentCoursesService.completeSet(courseId, stageId, setId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PostMapping(value = "/course/{courseId}/stage/{stageId}/content/complete")
    public ResponseEntity<?> completeStage(@PathVariable Long courseId, @PathVariable Long stageId)
    {
        try
        {
            restStudentCoursesService.completeStage(courseId, stageId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }
}
