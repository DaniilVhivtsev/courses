package com.fitness.courses.http.greeting.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.coach.course.model.dto.CourseVariableTypeEnum;
import com.fitness.courses.http.coach.course.model.dto.greeting.GreetingUpdateDto;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.service.CourseService;
import com.fitness.courses.http.coach.course.service.CourseValidator;
import com.fitness.courses.http.coach.variable.model.entity.CourseVariableEntity;
import com.fitness.courses.http.coach.variable.service.CourseVariableService;
import com.fitness.courses.http.coach.variable.service.CourseVariableValidator;
import com.fitness.courses.http.greeting.model.dto.CourseVariableDto;
import com.fitness.courses.http.greeting.model.dto.GreetingContent;
import com.fitness.courses.http.greeting.model.dto.StudentGreetingContent;
import com.fitness.courses.http.student.service.student.StudentValidator;
import com.fitness.courses.http.student.variable.model.dto.CourseVariableWithStudentValue;
import com.fitness.courses.http.student.variable.service.RestStudentVariableService;

@Service
public class RestGreetingServiceImpl implements RestGreetingService
{
    private final CourseValidator courseValidator;
    private final CourseService courseService;
    private final CourseVariableValidator courseVariableValidator;
    private final CourseVariableService courseVariableService;
    private final AuthService authService;
    private final StudentValidator studentValidator;
    private final RestStudentVariableService restStudentVariableService;

    @Autowired
    public RestGreetingServiceImpl(CourseValidator courseValidator,
            CourseService courseService,
            CourseVariableValidator courseVariableValidator,
            CourseVariableService courseVariableService, AuthService authService,
            StudentValidator studentValidator,
            RestStudentVariableService restStudentVariableService)
    {
        this.courseValidator = courseValidator;
        this.courseService = courseService;
        this.courseVariableValidator = courseVariableValidator;
        this.courseVariableService = courseVariableService;
        this.authService = authService;
        this.studentValidator = studentValidator;
        this.restStudentVariableService = restStudentVariableService;
    }

    // TODO добавить проверку на то, есть ли данная переменная в формулах в контенте курса
    @Override
    @Transactional
    public void update(Long courseId, GreetingUpdateDto greetingUpdateDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        courseValidator.validateGreetingTitle(greetingUpdateDto.getTitle());
        courseService.updateGreetingTitle(courseId, greetingUpdateDto.getTitle());

        courseVariableValidator.updateCourseVariables(courseId, greetingUpdateDto.getVariableDtoList());
        // При обновлении можно изменить тип у имеющегося переменной или можно солздать новую переменную. Нужно
        // добавить идентификатор переменной. Также проверять, чтобы не было одинаковых по коду переменных

        // Если изменяем код у переменной, нужно проверять есть ли переменная в формуле, если есть - выкидываем
        // исключение.

        courseVariableService.update(courseId, greetingUpdateDto.getVariableDtoList());
    }

    @Override
    public GreetingContent getGreetingContent(@NotNull Long courseId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        final CourseEntity courseEntity = courseService.getCourseOrThrow(courseId);
        final String courseGreetingTitle = courseEntity.getGreetingTitle();

        final List<CourseVariableEntity> courseVariableEntities =
                courseVariableService.findAllByCourseId(courseId);

        return new GreetingContent()
                .setCourseGreetingTitle(courseGreetingTitle)
                .setVariables(
                        courseVariableEntities.stream()
                                .map(courseVariableEntity -> new CourseVariableDto()
                                        .setCode(courseVariableEntity.getCode())
                                        .setTitle(courseVariableEntity.getTitle())
                                        .setType(CourseVariableTypeEnum.valueOf(courseVariableEntity.getType().name())))
                                .toList()
                );
    }

    @Override
    public StudentGreetingContent getStudentGreetingContent(@NotNull Long courseId)
    {
        courseValidator.validateCourseExist(courseId);

        final CourseEntity courseEntity = courseService.getCourseOrThrow(courseId);
        final String courseGreetingTitle = courseEntity.getGreetingTitle();

        final List<CourseVariableWithStudentValue> courseVariablesWithStudentValues =
                restStudentVariableService.getCourseVariablesWithStudentValues(courseId);

        return new StudentGreetingContent()
                .setCourseGreetingTitle(courseGreetingTitle)
                .setCourseVariablesWithStudentValues(courseVariablesWithStudentValues);
    }
}
