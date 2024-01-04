package com.fitness.courses.http.catalog.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.pagination.PaginationValidator;
import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.catalog.mapper.CatalogMapper;
import com.fitness.courses.http.catalog.model.dto.CatalogBySearchValueCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogNewCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogPopularCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.content.StageInfoDto;
import com.fitness.courses.http.coach.course.content.service.lesson.LessonService;
import com.fitness.courses.http.coach.course.content.service.lesson.LessonValidator;
import com.fitness.courses.http.coach.course.content.service.stage.StageService;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.service.CourseService;
import com.fitness.courses.http.coach.course.service.CourseValidator;
import com.fitness.courses.http.student.service.student.StudentService;
import com.fitness.courses.http.user.model.User;

@Service
public class RestCatalogServiceImpl implements RestCatalogService
{
    private final CourseService courseService;
    private final CourseValidator courseValidator;
    private final PaginationValidator paginationValidator;
    private final CatalogMapper catalogMapper;
    private final CatalogValidator catalogValidator;
    private final LessonValidator lessonValidator;
    private final StageService stageService;
    private final LessonService lessonService;
    private final AuthService authService;
    private final StudentService studentService;

    @Autowired
    public RestCatalogServiceImpl(CourseService courseService,
            CourseValidator courseValidator, PaginationValidator paginationValidator,
            CatalogMapper catalogMapper, CatalogValidator catalogValidator,
            LessonValidator lessonValidator,
            StageService stageService,
            LessonService lessonService, AuthService authService,
            StudentService studentService)
    {
        this.courseService = courseService;
        this.courseValidator = courseValidator;
        this.paginationValidator = paginationValidator;
        this.catalogMapper = catalogMapper;
        this.catalogValidator = catalogValidator;
        this.lessonValidator = lessonValidator;
        this.stageService = stageService;
        this.lessonService = lessonService;
        this.authService = authService;
        this.studentService = studentService;
    }

    @Override
    public List<StageInfoDto> getLessonStagesInfo(@NotNull Long courseId, @NotNull Long lessonId)
    {
        courseValidator.validateCourseExist(courseId);
        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToCourse(courseId, lessonId);
        final CourseEntity course = courseService.getCourseOrThrow(courseId);

        Optional<User> currentUserOptional = authService.getCurrentUser();
        Set<String> doneStageAndSetUuids = new HashSet<>();
        currentUserOptional.flatMap(user -> studentService.getByUserAndCourse(user, course))
                .ifPresent(student -> doneStageAndSetUuids.addAll(student.getDoneStageAndSetUuids()));

        return stageService.findAllByLessonAndSortAscBySerialNumber(lessonService.getOrThrow(lessonId)).stream()
                .map(stage -> catalogMapper.toStageInfoDto(stage, doneStageAndSetUuids))
                .toList();
    }

    @Override
    public List<CatalogNewCourseInfoDto> getNewCourses(Integer offset, Integer limit)
    {
        paginationValidator.validateOffsetValue(offset);
        paginationValidator.validateLimitValue(limit);

        return courseService.getNewCourses(offset, limit).stream()
                .map(catalogMapper::toCatalogNewCourseInfoDto)
                .toList();
    }

    @Override
    public CourseInfoDto getCourseInfo(Long courseId)
    {
        courseValidator.validateCourseExist(courseId);
        final CourseEntity course = courseService.getCourseOrThrow(courseId);

        return catalogMapper.toCourseInfoDto(course);
    }

    @Override
    public List<CatalogPopularCourseInfoDto> getPopularCourses(Integer offset, Integer limit)
    {
        paginationValidator.validateOffsetValue(offset);
        paginationValidator.validateLimitValue(limit);

        return courseService.getPopularCourses(offset, limit).stream()
                .map(catalogMapper::toCatalogPopularCourseInfoDto)
                .toList();
    }

    @Override
    public List<CatalogBySearchValueCourseInfoDto> getCoursesBySearchValue(String searchValue, Integer offset,
            Integer limit)
    {
        paginationValidator.validateOffsetValue(offset);
        paginationValidator.validateLimitValue(limit);

        catalogValidator.validateSearchValue(searchValue);

        return courseService.findAllByKeyword(searchValue, offset, limit).stream()
                .map(catalogMapper::toCatalogBySearchValueCourseInfoDto)
                .toList();
    }
}
