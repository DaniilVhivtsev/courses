package com.fitness.courses.http.student.service.student;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.service.CourseValidatorImpl;
import com.fitness.courses.http.user.model.User;

@Service
public class StudentValidatorImpl implements StudentValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(CourseValidatorImpl.class);

    private final StudentService studentService;

    @Autowired
    public StudentValidatorImpl(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @Override
    public void validateStudentWithUserAndCourseNotExist(@NotNull User user, @NotNull CourseEntity course)
            throws ValidationException
    {
        if (studentService.getByUserAndCourse(user, course).isPresent())
        {
            final String message = "There is already a user on the course.";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateStudentWithUserAndCourseExist(@NotNull User user, @NotNull CourseEntity course)
            throws ValidationException
    {
        if (studentService.getByUserAndCourse(user, course).isEmpty())
        {
            final String message = "The user is not a student of the course.";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
