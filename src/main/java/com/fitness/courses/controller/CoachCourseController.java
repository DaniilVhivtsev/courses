package com.fitness.courses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.global.exceptions.ResponseErrorException;
import com.fitness.courses.http.coach.card.model.dto.CardInfoDto;
import com.fitness.courses.http.coach.card.model.dto.ListCardInfoDto;
import com.fitness.courses.http.coach.card.model.dto.NewCardDto;
import com.fitness.courses.http.coach.card.service.RestCardService;
import com.fitness.courses.http.coach.course.content.model.dto.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.NewCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.UpdateCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.UpdateCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorContentInfo;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorGeneralInfoDto;
import com.fitness.courses.http.coach.course.model.dto.EditCourseAuthorGeneralInfo;
import com.fitness.courses.http.coach.course.model.dto.ListCourseInfoDto;
import com.fitness.courses.http.coach.course.model.dto.NewCourseDto;
import com.fitness.courses.http.coach.course.service.RestCourseService;
import com.fitness.courses.http.user.dto.UserGeneralInfoDto;

@RestController
@RequestMapping(path = "/coach")
public class CoachCourseController
{
    private final RestCourseService restCourseService;
    private final RestCardService restCardService;

    @Autowired
    public CoachCourseController(RestCourseService restCourseService,
            RestCardService restCardService)
    {
        this.restCourseService = restCourseService;
        this.restCardService = restCardService;
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

    @GetMapping(value = "/course/author/get/all")
    public ResponseEntity<?> getAllAuthorCourses()
    {
        try
        {
            return new ResponseEntity<List<ListCourseInfoDto>>(restCourseService.getAuthorCourses(), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @GetMapping(value = "/course/author/{id}/info")
    public ResponseEntity<?> getAuthorCourseGeneralInfo(@PathVariable Long id)
    {
        try
        {
            return new ResponseEntity<CourseAuthorGeneralInfoDto>(
                    restCourseService.getAuthorCourseGeneralInfo(id),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PutMapping(value = "/course/author/{id}/info")
    public ResponseEntity<?> editAuthorCourseGeneralInfo(@PathVariable Long id,
            @ModelAttribute EditCourseAuthorGeneralInfo editCourseAuthorGeneralInfo)
    {
        try
        {
            return new ResponseEntity<CourseAuthorGeneralInfoDto>(
                    restCourseService.editAuthCourseGeneralInfo(id, editCourseAuthorGeneralInfo),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @GetMapping(value = "/course/author/{id}/students")
    public ResponseEntity<?> getAuthorCourseStudentsInfo(@PathVariable Long id)
    {
        try
        {
            return new ResponseEntity<List<UserGeneralInfoDto>>(
                    restCourseService.getAuthorCourseStudents(id),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @GetMapping(value = "/course/author/{id}/info/content")
    public ResponseEntity<?> getAuthorCourseContent(@PathVariable Long id)
    {
        try
        {
            return new ResponseEntity<CourseAuthorContentInfo>(
                    restCourseService.getAuthorCourseContent(id),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PostMapping(value = "/course/author/{id}/info/content/module", consumes = "application/json")
    public ResponseEntity<?> addModuleToAuthorCourseContent(@PathVariable Long id,
            @RequestBody NewCourseAuthorModuleDto newModuleDto)
    {
        try
        {
            restCourseService.addModule(id, newModuleDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PatchMapping(value = "/course/author/{id}/info/content/module/{moduleId}", consumes = "application/json")
    public ResponseEntity<?> editModuleToAuthorCourseContent(@PathVariable Long id, @PathVariable Long moduleId,
            @RequestBody UpdateCourseAuthorModuleDto updateModuleDto)
    {
        try
        {
            restCourseService.editModule(id, moduleId, updateModuleDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @DeleteMapping(value = "/course/author/{id}/info/content/module/{moduleId}")
    public ResponseEntity<?> deleteModuleToAuthorCourseContent(@PathVariable Long id, @PathVariable Long moduleId)
    {
        try
        {
            restCourseService.deleteModule(id, moduleId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PostMapping(value = "/course/author/{id}/info/content/module/{moduleId}/lesson", consumes = "application/json")
    public ResponseEntity<?> addLessonToAuthorCourseContent(@PathVariable Long id, @PathVariable Long moduleId,
            @RequestBody NewCourseAuthorLessonDto newLessonDto)
    {
        try
        {
            restCourseService.addLesson(id, moduleId, newLessonDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PatchMapping(
            value = "/course/author/{id}/info/content/module/{moduleId}/lesson/{lessonId}",
            consumes = "application/json"
    )
    public ResponseEntity<?> editLessonToAuthorCourseContent(@PathVariable Long id, @PathVariable Long moduleId,
            @PathVariable Long lessonId, @RequestBody UpdateCourseAuthorLessonDto updateLessonDto)
    {
        try
        {
            restCourseService.editLesson(id, moduleId, lessonId, updateLessonDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @DeleteMapping(value = "/course/author/{id}/info/content/module/{moduleId}/lesson/{lessonId}")
    public ResponseEntity<?> deleteLessonToAuthorCourseContent(@PathVariable Long id, @PathVariable Long moduleId,
            @PathVariable Long lessonId)
    {
        try
        {
            restCourseService.deleteLesson(id, moduleId, lessonId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @PostMapping(value = "/card/create", consumes = "multipart/form-data")
    public ResponseEntity<?> createCard(@ModelAttribute NewCardDto newCardDto)
    {
        try
        {
            restCardService.createCard(newCardDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @GetMapping(value = "/card")
    public ResponseEntity<?> getCard(@RequestParam Long id)
    {
        try
        {
            return new ResponseEntity<CardInfoDto>(restCardService.getCard(id), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @GetMapping(value = "/card/user/get/all")
    public ResponseEntity<?> getAllCurrentUserCards()
    {
        try
        {
            return new ResponseEntity<List<ListCardInfoDto>>(restCardService.getUserCards(), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    // TODO getAttachment
}
