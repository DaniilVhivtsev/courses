package com.fitness.courses.http.coach.course.service;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.user.model.User;

@Service
public class CourseValidatorImpl implements CourseValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(CourseValidatorImpl.class);

    private final AuthService authService;
    private final CourseService courseService;

    @Autowired
    public CourseValidatorImpl(AuthService authService,
            CourseService courseService)
    {
        this.authService = authService;
        this.courseService = courseService;
    }

    @Override
    public void validateCourseTitle(String title)
    {
        if (StringUtils.isBlank(title))
        {
            final String message = "Course title is blank";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateCourseExist(final @NotNull Long id) throws ValidationException
    {
        if (courseService.getCourseOptional(id).isEmpty())
        {
            final String message = "Course not exist";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateCurrentUserHasPermission(final @NotNull Long courseId) throws ValidationException
    {
        final User currentUser = authService.getCurrentUserOrThrow();
        final CourseEntity course = courseService.getCourseOrThrow(courseId);

        if (!course.getAuthor().getId().equals(currentUser.getId()))
        {
            final String message = "The user doesn't have permission to work with course";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
