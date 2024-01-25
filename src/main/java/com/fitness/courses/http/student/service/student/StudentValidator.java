package com.fitness.courses.http.student.service.student;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.user.model.User;

public interface StudentValidator
{
    void validateStudentWithUserAndCourseNotExist(@NotNull User user, @NotNull CourseEntity course)
            throws ValidationException;

    void validateStudentWithUserAndCourseExist(@NotNull User user, @NotNull CourseEntity course)
            throws ValidationException;
}
