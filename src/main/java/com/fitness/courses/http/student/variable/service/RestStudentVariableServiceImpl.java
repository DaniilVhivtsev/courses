package com.fitness.courses.http.student.variable.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.coach.course.model.dto.CourseVariableTypeEnum;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.service.CourseService;
import com.fitness.courses.http.coach.variable.model.entity.CourseVariableEntity;
import com.fitness.courses.http.coach.variable.service.CourseVariableService;
import com.fitness.courses.http.student.model.entity.StudentEntity;
import com.fitness.courses.http.student.service.student.StudentService;
import com.fitness.courses.http.student.service.student.StudentValidator;
import com.fitness.courses.http.student.variable.model.dto.CourseVariableWithStudentValue;
import com.fitness.courses.http.student.variable.model.dto.StudentVariableUpdatedValue;
import com.fitness.courses.http.student.variable.model.entity.StudentVariableEntity;
import com.fitness.courses.http.student.variable.model.info.CourseVariableWithStudentValueInfo;
import com.fitness.courses.http.user.model.User;

@Service
public class RestStudentVariableServiceImpl implements RestStudentVariableService
{
    private final StudentVariableService studentVariableService;
    private final CourseVariableService courseVariableService;
    private final AuthService authService;
    private final CourseService courseService;
    private final StudentValidator studentValidator;
    private final StudentService studentService;

    @Autowired
    public RestStudentVariableServiceImpl(
            StudentVariableService studentVariableService,
            CourseVariableService courseVariableService, AuthService authService,
            CourseService courseService, StudentValidator studentValidator,
            StudentService studentService)
    {
        this.studentVariableService = studentVariableService;
        this.courseVariableService = courseVariableService;
        this.authService = authService;
        this.courseService = courseService;
        this.studentValidator = studentValidator;
        this.studentService = studentService;
    }

    @Override
    public void updateStudentVariableValues(@NotNull Long courseId,
            @NotNull List<StudentVariableUpdatedValue> studentVariableUpdatedValues)
    {
        final User currentUser = authService.getCurrentUserOrThrow();
        final CourseEntity course = courseService.getCourseOrThrow(courseId);
        studentValidator.validateStudentWithUserAndCourseExist(currentUser, course);

        final StudentEntity student = studentService.getByUserAndCourse(currentUser, course).orElseThrow();

        studentVariableUpdatedValues.forEach(studentVariableUpdatedValue ->
        {
            studentVariableService.findAllByCourseVariableIdAndStudentId(studentVariableUpdatedValue.getId(),
                            student.getId())
                    .ifPresentOrElse(
                            studentVariableEntity ->
                            {
                                studentVariableService.update(
                                        studentVariableEntity.setValue(studentVariableUpdatedValue.getValue()));
                            },
                            () ->
                            {
                                StudentVariableEntity newStudentVariable = new StudentVariableEntity();
                                newStudentVariable.setValue(studentVariableUpdatedValue.getValue());
                                newStudentVariable.setCourseVariable(courseVariableService.findByIdOrThrow(
                                        studentVariableUpdatedValue.getId()));
                                newStudentVariable.setStudent(student);

                                studentVariableService.addNew(newStudentVariable);
                            });
        });
    }

    @Override
    public boolean checkNeedToFillValuesInVariables(Long courseId)
    {
        return getCourseVariablesWithStudentValues(courseId).stream()
                .anyMatch(courseVariableWithStudentValue -> courseVariableWithStudentValue.getStudentValue() == null);
    }

    @Override
    public List<CourseVariableWithStudentValue> getCourseVariablesWithStudentValues(Long courseId)
    {
        return getCourseVariablesWithStudentValuesInfo(courseId).stream()
                .map(courseVariableWithStudentValueInfo -> new CourseVariableWithStudentValue()
                        .setId(courseVariableWithStudentValueInfo.getId())
                        .setTitle(courseVariableWithStudentValueInfo.getTitle())
                        .setStudentValue(courseVariableWithStudentValueInfo.getStudentValue())
                        .setType(courseVariableWithStudentValueInfo.getType()))
                .toList();
    }

    @Override
    public List<CourseVariableWithStudentValueInfo> getCourseVariablesWithStudentValuesInfo(Long courseId)
    {
        final User currentUser = authService.getCurrentUserOrThrow();
        final CourseEntity course = courseService.getCourseOrThrow(courseId);
        studentValidator.validateStudentWithUserAndCourseExist(currentUser, course);

        final List<CourseVariableEntity> courseVariables = courseVariableService.findAllByCourseId(courseId);
        StudentEntity student = studentService.getByUserAndCourseOrThrow(currentUser, course);

        return courseVariables.stream()
                .map(courseVariable ->
                {
                    final Optional<StudentVariableEntity> studentVariableEntityOptional =
                            studentVariableService.findAllByCourseVariableIdAndStudentId(courseVariable.getId(),
                                    student.getId());

                    final Float value = studentVariableEntityOptional.map(StudentVariableEntity::getValue).orElse(null);

                    return new CourseVariableWithStudentValueInfo()
                            .setId(courseVariable.getId())
                            .setTitle(courseVariable.getTitle())
                            .setCode(courseVariable.getCode())
                            .setType(CourseVariableTypeEnum.valueOf(courseVariable.getType().name()))
                            .setStudentValue(value);
                })
                .toList();
    }
}
