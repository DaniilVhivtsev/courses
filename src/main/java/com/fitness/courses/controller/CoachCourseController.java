package com.fitness.courses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.fitness.courses.global.exceptions.ResponseErrorException;
import com.fitness.courses.http.coach.card.model.dto.CardInfoDto;
import com.fitness.courses.http.coach.card.model.dto.ListCardInfoDto;
import com.fitness.courses.http.coach.card.model.dto.NewCardDto;
import com.fitness.courses.http.coach.card.service.RestCardService;
import com.fitness.courses.http.coach.course.content.model.dto.lesson.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.lesson.UpdateCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.module.NewCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.module.UpdateCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.AddCourseAuthorStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageWithContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.UpdateCourseAuthorStageDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.StageContentType;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateExercisesStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateImgStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateTextStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateVideoStageContentDto;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorContentInfo;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorGeneralInfoDto;
import com.fitness.courses.http.coach.course.model.dto.EditCourseAuthorGeneralInfo;
import com.fitness.courses.http.coach.course.model.dto.ListCourseInfoDto;
import com.fitness.courses.http.coach.course.model.dto.NewCourseDto;
import com.fitness.courses.http.coach.course.model.dto.greeting.GreetingUpdateDto;
import com.fitness.courses.http.coach.course.service.RestCourseService;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionDto;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionGetResultDto;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionResultDto;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionVariableDto;
import com.fitness.courses.http.coach.variable.service.CourseVariableService;
import com.fitness.courses.http.coach.variable.service.RestCourseVariableService;
import com.fitness.courses.http.greeting.model.dto.GreetingContent;
import com.fitness.courses.http.greeting.service.RestGreetingService;
import com.fitness.courses.http.user.dto.UserGeneralInfoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Rest контроллер связанный с работой автора курса с курсом.
 */
@Tag(
        name = "Контроллер работы автора курса с курсом.",
        description = "Rest контроллер связанный с работой автора курса с курсом."
)
@RestController
@RequestMapping(path = "/coach")
public class CoachCourseController
{
    private final RestCourseService restCourseService;
    private final RestCardService restCardService;
    private final RestGreetingService restGreetingService;
    private final CourseVariableService courseVariableService;
    private final RestCourseVariableService restCourseVariableService;

    @Autowired
    public CoachCourseController(
            RestCourseService restCourseService,
            RestCardService restCardService,
            RestGreetingService restGreetingService,
            CourseVariableService courseVariableService,
            RestCourseVariableService restCourseVariableService)
    {
        this.restCourseService = restCourseService;
        this.restCardService = restCardService;
        this.restGreetingService = restGreetingService;
        this.courseVariableService = courseVariableService;
        this.restCourseVariableService = restCourseVariableService;
    }

    @Operation(
            summary = "Post метод создания нового курса.",
            description = "Post метод создания нового курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый курс успешно создан. Возвращается идентификатор курса.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Long.class
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
    @PostMapping(value = "/course/create", consumes = "application/json")
    public ResponseEntity<?> createCourse(@RequestBody NewCourseDto newCourseDto)
    {
        try
        {
            return new ResponseEntity<Long>(restCourseService.createCourse(newCourseDto), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Delete метод удаления курса.",
            description = "Delete метод удаления курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Курс успешно удален.",
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
    @DeleteMapping(value = "/course/author/{id}")
    public ResponseEntity<?> deleteAuthorCourseGeneralInfo(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id)
    {
        try
        {
            restCourseService.deleteCourse(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Get метод получения курсов пользователя, где он является автором.",
            description = "Get метод получения курсов пользователя, где он является автором."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список курсов пользователя, где он является автором, успешно возвращен.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = ListCourseInfoDto.class
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

    @Operation(
            summary = "Get метод получения основной информации о курсе для автора.",
            description = "Get метод получения курсов пользователя, где он является автором."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Основная информация о курсе для автора успешно возвращена.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CourseAuthorGeneralInfoDto.class
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
    @GetMapping(value = "/course/author/{id}/info")
    public ResponseEntity<?> getAuthorCourseGeneralInfo(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id)
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

    @Operation(
            summary = "Put метод изменения автором основной информации о курсе.",
            description = "Put метод изменения автором основной информации о курсе.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = EditCourseAuthorGeneralInfo.class)
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Основная информация о курсе автором успешно изменена.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CourseAuthorGeneralInfoDto.class
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
    @PutMapping(value = "/course/author/{id}/info")
    public ResponseEntity<?> editAuthorCourseGeneralInfo(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
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

    @Operation(
            summary = "Get метод получения списка студентов на курсе.",
            description = "Get метод получения списка студентов на курсе."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список студентов на курсе успешно возвращен.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = UserGeneralInfoDto.class
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
    @GetMapping(value = "/course/author/{id}/students")
    public ResponseEntity<?> getAuthorCourseStudentsInfo(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id)
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

    @Operation(
            summary = "Get метод получения контента (модули и занятия) курса для автора.",
            description = "Get метод получения контента (модули и занятия) курса для автора."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Контент курса успешно возвращен.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CourseAuthorContentInfo.class
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
    @GetMapping(value = "/course/author/{id}/info/content")
    public ResponseEntity<?> getAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id)
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

    @Operation(
            summary = "Post метод добавления модуля в курс.",
            description = "Post метод добавления модуля в курс."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Модуль успешно добавлен. Возвращается идентификатор модуля.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Long.class
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
    @PostMapping(value = "/course/author/{id}/info/content/module", consumes = "application/json")
    public ResponseEntity<?> addModuleToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @RequestBody NewCourseAuthorModuleDto newModuleDto)
    {
        try
        {
            return new ResponseEntity<Long>(restCourseService.addModule(id, newModuleDto), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Patch метод обновление модуля в курсе.",
            description = "Patch метод обновление модуля в курсе."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Модуль успешно обновлен.",
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
    @PatchMapping(value = "/course/author/{id}/info/content/module/{moduleId}", consumes = "application/json")
    public ResponseEntity<?> editModuleToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @PathVariable Long moduleId,
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

    @Operation(
            summary = "Delete метод удаления модуля из курса.",
            description = "Delete метод удаления модуля из курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Модуль успешно удален.",
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
    @DeleteMapping(value = "/course/author/{id}/info/content/module/{moduleId}")
    public ResponseEntity<?> deleteModuleToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @PathVariable Long moduleId)
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

    @Operation(
            summary = "Post метод добавления занятия в модуль курса.",
            description = "Post метод добавления занятия в модуль курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Занятие успешно добавлено. Возвращается идентификатор занятия.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Long.class
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
    @PostMapping(value = "/course/author/{id}/info/content/module/{moduleId}/lesson", consumes = "application/json")
    public ResponseEntity<?> addLessonToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @PathVariable Long moduleId,
            @RequestBody NewCourseAuthorLessonDto newLessonDto)
    {
        try
        {
            return new ResponseEntity<Long>(restCourseService.addLesson(id, moduleId, newLessonDto), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Patch метод редактирования занятия в модуле курса.",
            description = "Patch метод редактирования занятия в модуле курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Занятие успешно обновлено.",
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
    @PatchMapping(
            value = "/course/author/{id}/info/content/module/{moduleId}/lesson/{lessonId}",
            consumes = "application/json"
    )
    public ResponseEntity<?> editLessonToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @PathVariable Long moduleId,
            @PathVariable Long lessonId,
            @RequestBody UpdateCourseAuthorLessonDto updateLessonDto)
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

    @Operation(
            summary = "Delete метод удаления занятия из модуля курса.",
            description = "Delete метод удаления занятия из модуля курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Занятие успешно удалено.",
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
    @DeleteMapping(value = "/course/author/{id}/info/content/module/{moduleId}/lesson/{lessonId}")
    public ResponseEntity<?> deleteLessonToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @PathVariable Long moduleId,
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

    @Operation(
            summary = "Post метод добавления этапа в занятие курса.",
            description = "Post метод добавления этапа в занятие курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Этап успешно добавлен. Возвращается идентификатор этапа.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Long.class
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
    @PostMapping(value = "/course/author/{id}/info/content/lesson/{lessonId}/stage/title/{stageTitle}")
    public ResponseEntity<?> addStageToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @PathVariable Long lessonId,
            @PathVariable String stageTitle)
    {
        try
        {
            return new ResponseEntity<Long>(restCourseService.addStage(id, lessonId, stageTitle), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Get метод получения списка этапов занятия.",
            description = "Get метод получения списка этапов занятия."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список этапов успешно возвращен.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = CourseAuthorStageInfoDto.class
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
    @GetMapping(value = "/course/author/{id}/info/content/lesson/{lessonId}/stages")
    public ResponseEntity<?> getStagesToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @PathVariable Long lessonId)
    {
        try
        {
            return new ResponseEntity<List<CourseAuthorStageInfoDto>>(
                    restCourseService.getStages(id, lessonId),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Get метод получения этапа занятия с контентом.",
            description = "Get метод получения этапа занятия с контентом."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Этап занятия с контентом успешно возвращен.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CourseAuthorStageWithContentInfoDto.class
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
    @GetMapping(value = "/course/author/{id}/info/content/lesson/{lessonId}/stages/{stageId}/content")
    public ResponseEntity<?> getStageWithContentToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @PathVariable Long lessonId,
            @PathVariable Long stageId)
    {
        try
        {
            return new ResponseEntity<CourseAuthorStageWithContentInfoDto>(
                    restCourseService.getStage(id, lessonId, stageId),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Get метод получения списка этапов занятия с контентом.",
            description = "Get метод получения списка этапов занятия с контентом."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список этапов занятия с контентом успешно возвращен.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = CourseAuthorStageWithContentInfoDto.class
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
    @GetMapping(value = "/course/author/{id}/info/content/lesson/{lessonId}/stages/content")
    public ResponseEntity<?> getStagesWithContentToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @PathVariable Long lessonId)
    {
        try
        {
            return new ResponseEntity<List<CourseAuthorStageWithContentInfoDto>>(
                    restCourseService.getStagesWithContent(id, lessonId),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Patch метод обновления основной информации этапа занятия курса.",
            description = "Patch метод обновления основной информации этапа занятия курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Основная информация этапа занятия успешно обновлена.",
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
    @PatchMapping(
            value = "/course/author/{id}/info/content/lesson/{lessonId}/stages/{stageId}",
            consumes = "application/json"
    )
    public ResponseEntity<?> editStageToAuthorCourseContent(
            @PathVariable Long id,
            @Parameter(description = "Идентификатор курса.") @PathVariable Long lessonId,
            @PathVariable Long stageId,
            @RequestBody UpdateCourseAuthorStageDto updateStageDto)
    {
        try
        {
            restCourseService.editStage(id, lessonId, stageId, updateStageDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Delete метод удаления этапа занятия курса.",
            description = "Delete метод удаления этапа занятия курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Этап занятия успешно удален.",
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
    @DeleteMapping(value = "/course/author/{id}/info/content/lesson/{lessonId}/stages/{stageId}")
    public ResponseEntity<?> deleteStageToAuthorCourseContent(
            @Parameter(description = "Идентификатор курса.") @PathVariable Long id,
            @PathVariable Long lessonId,
            @PathVariable Long stageId)
    {
        try
        {
            restCourseService.deleteStage(id, lessonId, stageId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Post метод добавления контента в этап занятие курса.",
            description = "Post метод добавления контента в этап занятие курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Контент в этап успешно добавлен. Возвращается идентификатор "
                                    + "добавленного контента.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = String.class
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
    @PostMapping(value = "/course/author/{id}/info/content/lesson/{lessonId}/stage/{stageId}/content")
    public ResponseEntity<?> addContentToStageToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @PathVariable Long lessonId,
            @PathVariable Long stageId,
            @RequestBody AddCourseAuthorStageContentInfoDto addContentDto)
    {
        try
        {
            return new ResponseEntity<String>(
                    restCourseService.addStageContent(id, lessonId, stageId, addContentDto),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Patch метод обновления контента у этапа.",
            description = "Patch метод обновления контента у этапа.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "multipart/form-data",
                                    schema = @Schema(
                                            description = "Enum нужно обязательно. Ключ для него type",
                                            oneOf = {
                                                    UpdateExercisesStageContentDto.class,
                                                    UpdateImgStageContentDto.class,
                                                    UpdateTextStageContentDto.class,
                                                    UpdateVideoStageContentDto.class
                                            },
                                            anyOf = StageContentType.class
                                    )
                            )
                    }
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Контент у этапа успешно обновлен.",
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
    @PatchMapping(
            value = "/course/author/{id}/info/content/lesson/{lessonId}/stage/{stageId}/content",
            //            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> updateContentToStageToAuthorCourseContent(
            @PathVariable
            @Parameter(description = "Идентификатор курса.") Long id,
            @PathVariable Long lessonId,
            @PathVariable Long stageId,
            @ModelAttribute("type") StageContentType type,
            @Parameter(hidden = true)
            @RequestParam MultiValueMap<String, Object> formData,
            HttpServletRequest request)
    {
        // TODO custom deserializer
        try
        {
            MultiValueMap<String, MultipartFile> multiValueMap =
                    ((StandardMultipartHttpServletRequest)request).getMultiFileMap();
            MultipartFile multipartFile = multiValueMap.containsKey("image")
                    ? multiValueMap.get("image").get(0)
                    : multiValueMap.containsKey("video") ? multiValueMap.get("video").get(0) : null;
            //            formData.remove("type");
            restCourseService.editStageContent(id, lessonId, stageId, type, formData, multipartFile);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Post метод добавления карточки с занятием.",
            description = "Post метод добавления карточки с занятием.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "multipart/form-data",
                                    schema = @Schema(
                                            implementation = NewCardDto.class
                                    )
                            )
                    }
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Карточка с занятием успешно создана. Возвращается идентификатор карточки.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Long.class
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
    @PostMapping(value = "/card/create", consumes = "multipart/form-data")
    public ResponseEntity<?> createCard(@ModelAttribute NewCardDto newCardDto)
    {
        try
        {
            return new ResponseEntity<Long>(restCardService.createCard(newCardDto), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Get метод получения полной информации о карточки с занятием.",
            description = "Get метод получения полной информации о карточки с занятием."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Полная информация о карточке с занятием успешно возвращена.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CardInfoDto.class
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
    @GetMapping(value = "/card")
    public ResponseEntity<?> getCard(
            @RequestParam
            @Parameter(description = "Идентификатор карточки.") Long id)
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

    @Operation(
            summary = "Get метод получения списка карточек с занятиями текущего пользователя.",
            description = "Get метод получения списка карточек с занятиями текущего пользователя."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список карточек с занятиями текущего пользователя успешно возвращены.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = ListCardInfoDto.class
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

    @Operation(
            summary = "Get метод получения приветственного слова с переменными.",
            description = "Get метод получения приветственного слова с переменными."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Приветственное слово с переменными успешно возвращены.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GreetingContent.class
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
    @GetMapping(value = "/course/author/{id}/info/greeting")
    public ResponseEntity<?> getGreeting(@PathVariable @Parameter(description = "Идентификатор курса.") Long id)
    {
        try
        {
            return new ResponseEntity<GreetingContent>(
                    restGreetingService.getGreetingContent(id),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Post метод обновления приветственного слова с переменными.",
            description = "Post метод обновления приветственного слова с переменными.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GreetingUpdateDto.class
                                    )
                            )
                    }
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Приветственное слово с переменными успешно обновлены.",
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
    @PutMapping(value = "/course/author/{id}/info/greeting")
    public ResponseEntity<?> updateGreeting(
            @PathVariable @Parameter(description = "Идентификатор курса.") Long id,
            @RequestBody GreetingUpdateDto greetingUpdateDto)
    {
        try
        {
            restGreetingService.update(id, greetingUpdateDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Post метод получения переменных переданного математического выражения.",
            description = "Post метод получения переменных переданного математического выражения (Поиск происходит по "
                    + "переменным, которые были добавлены в курс).",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ExpressionDto.class
                                    )
                            )
                    }
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список переменных успешно возвращены.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = ExpressionVariableDto.class
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
    @PostMapping(value = "/course/author/{courseId}/expression/variables")
    public ResponseEntity<?> getExpressionVariables(
            @PathVariable @Parameter(description = "Идентификатор курса.") Long courseId,
            @RequestBody ExpressionDto expressionDto)
    {
        try
        {
            return new ResponseEntity<List<ExpressionVariableDto>>(
                    restCourseVariableService.getExpressionVariables(courseId, expressionDto),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Post метод получения результата переданного математического выражения со значениями в "
                    + "переменных.",
            description = "Post метод получения результата переданного математического выражения со значениями в "
                    + "переменных.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ExpressionGetResultDto.class
                                    )
                            )
                    }
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Результат математического выражения успешно возвращен.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ExpressionResultDto.class
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
    @PostMapping(value = "/course/author/{courseId}/expression/result")
    public ResponseEntity<?> getExpressionResult(
            @PathVariable @Parameter(description = "Идентификатор курса.") Long courseId,
            @RequestBody ExpressionGetResultDto expressionGetResultDto)
    {
        try
        {
            return new ResponseEntity<ExpressionResultDto>(
                    restCourseVariableService.getExpressionResult(courseId, expressionGetResultDto),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    // TODO getAttachment
}
