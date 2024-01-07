package com.fitness.courses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.global.exceptions.ResponseErrorException;
import com.fitness.courses.http.catalog.model.dto.CatalogBySearchValueCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogNewCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogPopularCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.content.StageInfoDto;
import com.fitness.courses.http.catalog.service.RestCatalogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Rest контроллер связанный с получением курсов в каталоге и получении публичной информации о конкретном курсе.
 */
@Tag(
        name = "Контроллер курсов в каталоге.",
        description = "Rest контроллер связанный с получением курсов в каталоге и получении публичной информации о "
                + "конкретном курсе."
)
@RestController
@RequestMapping("/public/course")
public class CatalogController
{
    private final RestCatalogService restCatalogService;

    @Autowired
    public CatalogController(RestCatalogService restCatalogService)
    {
        this.restCatalogService = restCatalogService;
    }

    @Operation(
            summary = "Get метод получения новых курсов в каталоге.",
            description = "Get метод возвращает курсы, которые недавно были опубликованы на площадке."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новые курсы были успешно возвращены.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = CatalogNewCourseInfoDto.class
                                            )
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @GetMapping("/new/all")
    public ResponseEntity<?> getNewCourses(
            @RequestParam(value = "offset", defaultValue = "0")
            @Parameter(description = "Номер страницы.") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20")
            @Parameter(description = "Количество элементов на странице.") Integer limit)
    {
        try
        {
            return new ResponseEntity<List<CatalogNewCourseInfoDto>>(
                    restCatalogService.getNewCourses(offset, limit),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Get метод получения популярных курсов в каталоге.",
            description = "Get метод возвращает популярные курсы (курсы с большим количеством студентов)."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Популярные курсы были успешно возвращены.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = CatalogPopularCourseInfoDto.class
                                            )
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @GetMapping("/popular/all")
    public ResponseEntity<?> getPopularCourses(
            @RequestParam(value = "offset", defaultValue = "0")
            @Parameter(description = "Номер страницы.") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20")
            @Parameter(description = "Количество элементов на странице.") Integer limit)
    {
        try
        {
            return new ResponseEntity<List<CatalogPopularCourseInfoDto>>(
                    restCatalogService.getPopularCourses(offset, limit),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Get метод получения курсов по ключевому слову.",
            description = "Get метод возвращает курсы по ключевому слову (поиск происходит в полях круса: title, "
                    + "shortDescription, about, author.firstName, author.lastName)."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденные курсы по ключевому слову были успешно возвращены.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = CatalogBySearchValueCourseInfoDto.class
                                            )
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @GetMapping("/searchBy")
    public ResponseEntity<?> getCoursesBySearchValue(
            @RequestParam("searchValue")
            @Parameter(description = "Ключевое слово.") String searchValue,
            @RequestParam(value = "offset", defaultValue = "0")
            @Parameter(description = "Номер страницы.") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20")
            @Parameter(description = "Количество элементов на странице.") Integer limit)
    {
        try
        {
            return new ResponseEntity<List<CatalogBySearchValueCourseInfoDto>>(
                    restCatalogService.getCoursesBySearchValue(searchValue, offset, limit),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Get метод получения информации (публичной и содержанием: модули, уроки) о курсе.",
            description = "Get метод получения информации (публичной и содержанием: модули, уроки) о курсе."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о курсе успешно возвращена.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CourseInfoDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @GetMapping("/info/{courseId}")
    public ResponseEntity<?> getCourseInfo(@PathVariable @Parameter(description = "Идентификатор курса.") Long courseId)
    {
        try
        {
            return new ResponseEntity<CourseInfoDto>(restCatalogService.getCourseInfo(courseId), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Get метод получения этапов урока курса.",
            description = "Get метод получения этапов урока курса."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список этапов урока курса успешно возвращены.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = StageInfoDto.class
                                            )
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @GetMapping("/info/{courseId}/lesson/{lessonId}/stages")
    public ResponseEntity<?> getLessonStagesInfo(
            @PathVariable @Parameter(description = "Идентификатор курса.") Long courseId,
            @PathVariable @Parameter(description = "Идентификатор урока.") Long lessonId)
    {
        try
        {
            return new ResponseEntity<List<StageInfoDto>>(
                    restCatalogService.getLessonStagesInfo(courseId, lessonId),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }
}
