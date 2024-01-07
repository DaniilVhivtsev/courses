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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Rest контроллер связанный с получением информации о текущем пользователе.
 */
@Tag(
        name = "Контроллер текущего пользователя.",
        description = "Rest контроллер связанный с получением информации о текущем пользователе."
)
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

    @Operation(
            summary = "Get метод получения курсов пользователя, которые он проходит.",
            description = "Get метод получения курсов пользователя, которые он проходит."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Курсы пользователя были успешно возвращены.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = UserCurrentCourseInfo.class
                                            )
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
