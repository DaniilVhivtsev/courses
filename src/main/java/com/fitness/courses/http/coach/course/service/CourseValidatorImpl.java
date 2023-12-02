package com.fitness.courses.http.coach.course.service;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;

@Service
public class CourseValidatorImpl implements CourseValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(CourseValidatorImpl.class);

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
}
