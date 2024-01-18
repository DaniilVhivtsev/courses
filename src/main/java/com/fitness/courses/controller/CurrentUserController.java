package com.fitness.courses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.global.exceptions.ResponseErrorException;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorGeneralInfoDto;
import com.fitness.courses.http.coach.course.model.dto.EditCourseAuthorGeneralInfo;
import com.fitness.courses.http.student.model.dto.achievement.UserCourseWithAchievementsInfoDto;
import com.fitness.courses.http.student.service.RestStudentCoursesService;
import com.fitness.courses.http.user.dto.GeneralInfo;
import com.fitness.courses.http.user.dto.UserCurrentCourseInfo;
import com.fitness.courses.http.user.model.dto.EditUserGeneralInfoDto;
import com.fitness.courses.http.user.service.RestUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private final RestUserService restUserService;

    @Autowired
    public CurrentUserController(
            RestStudentCoursesService restStudentCoursesService,
            RestUserService restUserService)
    {
        this.restStudentCoursesService = restStudentCoursesService;
        this.restUserService = restUserService;
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

    @Operation(
            summary = "Get метод получения основной информации о текущем пользователе.",
            description = "Get метод получения основной информации о текущем пользователе."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о текущем пользователе успешно возвращена.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GeneralInfo.class
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
    @GetMapping("/generalInfo")
    public ResponseEntity<?> getGeneralInfoForCurrentUser()
    {
        try
        {
            return new ResponseEntity<GeneralInfo>(
                    restUserService.getCurrentUserGeneralInfo(),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Get метод получения достижений текущего пользователя.",
            description = "Get метод получения достижений текущего пользователя."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Курсы c достижениями текущего пользователя были успешно возвращены.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = UserCourseWithAchievementsInfoDto.class
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
    @GetMapping("/achievements")
    public ResponseEntity<?> getCurrentUserAchievements()
    {
        try
        {
            return new ResponseEntity<List<UserCourseWithAchievementsInfoDto>>(
                    restUserService.getCurrentUserAchievements(),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Put метод изменения информации о текущем пользователе.",
            description = "Put метод изменения информации о текущем пользователе.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = EditUserGeneralInfoDto.class)
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о пользователе успешно изменена.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GeneralInfo.class
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
    @PutMapping(value = "/generalInfo")
    public ResponseEntity<?> editAuthorCourseGeneralInfo(
            @ModelAttribute EditUserGeneralInfoDto editUserGeneralInfoDto)
    {
        try
        {
            return new ResponseEntity<GeneralInfo>(
                    restUserService.editUserInfo(editUserGeneralInfoDto),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }
}
