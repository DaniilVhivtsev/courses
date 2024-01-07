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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Rest контроллер связанный с работой студента с курсом.
 */
@Tag(
        name = "Контроллер студента курса.",
        description = "Rest контроллер связанный с работой студента с курсом."
)
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

    @Operation(
            summary = "Get метод получения контента этапа курса.",
            description = "Get метод получения контента этапа курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Контент этапа курса был успешно возвращен.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = StageContentInfoDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Ошибка авторизации пользователя. Пользователь не авторизован или access "
                                    + "токен просрочился."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @SecurityRequirement(name = "JWT")
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

    @Operation(
            summary = "Post метод подачи заявки на регистрацию на курс. (Пока данный метод равнозначен регистрации "
                    + "пользователя на курс).",
            description = "Post метод подачи заявки на регистрацию на курс. (Пока данный метод равнозначен "
                    + "регистрации пользователя на курс)."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь удачно подал заявку на регистрацию на курс.",
                            content = {}
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Ошибка авторизации пользователя. Пользователь не авторизован или access "
                                    + "токен просрочился."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @SecurityRequirement(name = "JWT")
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

    @Operation(
            summary = "Post метод выполнения сета в этапе.",
            description = "Post метод выполнения сета в этапе."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь удачно подал заявку на регистрацию на курс.",
                            content = {}
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Ошибка авторизации пользователя. Пользователь не авторизован или access "
                                    + "токен просрочился."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @SecurityRequirement(name = "JWT")
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

    @Operation(
            summary = "Post метод выполнения этапа. (Вызывается, если в этапе отсутствуют упражнения).",
            description = "Post метод выполнения этапа. (Вызывается, если в этапе отсутствуют упражнения)."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь удачно подал заявку на регистрацию на курс.",
                            content = {}
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Ошибка авторизации пользователя. Пользователь не авторизован или access "
                                    + "токен просрочился."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @SecurityRequirement(name = "JWT")
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
