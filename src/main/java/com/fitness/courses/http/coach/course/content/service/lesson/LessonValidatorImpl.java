package com.fitness.courses.http.coach.course.content.service.lesson;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.service.CourseValidatorImpl;

@Service
public class LessonValidatorImpl implements LessonValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(CourseValidatorImpl.class);

    private final LessonService lessonService;

    @Autowired
    public LessonValidatorImpl(LessonService lessonService)
    {
        this.lessonService = lessonService;
    }

    @Override
    public void validateTitle(String title) throws ValidationException
    {
        if (StringUtils.isBlank(title))
        {
            final String message = "Lesson title is blank";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateExist(final @NotNull Long id) throws ValidationException
    {
        if (lessonService.getOptional(id).isEmpty())
        {
            final String message = "Lesson not exist";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
