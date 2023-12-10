package com.fitness.courses.http.coach.course.content.service.lesson;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.service.stage.StageService;
import com.fitness.courses.http.coach.course.service.CourseValidatorImpl;

@Service
public class LessonValidatorImpl implements LessonValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(CourseValidatorImpl.class);

    private final LessonService lessonService;
    private final StageService stageService;

    @Autowired
    public LessonValidatorImpl(
            LessonService lessonService,
            StageService stageService)
    {
        this.lessonService = lessonService;
        this.stageService = stageService;
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

    @Override
    public void validateLessonBelongsToModule(@NotNull Long moduleId, @NotNull Long lessonId) throws ValidationException
    {
        final LessonEntity lessonEntityFromDb = lessonService.getOrThrow(lessonId);

        if (!lessonEntityFromDb.getModule().getId().equals(moduleId))
        {
            final String message = "Lesson with id %d doesn't belong to module with id %d".formatted(lessonId,
                    moduleId);
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateSerialNumber(final @NotNull ModuleEntity moduleEntity, final @NotNull Integer serialNumber)
            throws ValidationException
    {
        if (serialNumber < 0)
        {
            final String message = "Serial number can't be less than 0 (zero)";
            LOG.error(message);
            throw new ValidationException(message);
        }

        Optional<LessonEntity> lessonEntityOptional =
                lessonService.findAllByModuleAndSortAscBySerialNumber(moduleEntity).stream()
                        .reduce((first, second) -> second).stream()
                        .findFirst();

        lessonEntityOptional.ifPresent(lessonEntity ->
        {
            if (serialNumber > lessonEntity.getSerialNumber())
            {
                final String message = "The new serial number is outside the available numbers. "
                        + "It cannot be greater than the serial number of the last lesson";
                LOG.error(message);
                throw new ValidationException(message);
            }
        });
    }

    @Override
    public void validateNoStagesInLesson(final @NotNull Long lessonId) throws ValidationException
    {
        final LessonEntity lessonEntityFromDb = lessonService.getOrThrow(lessonId);
        if (!stageService.findAllByLessonAndSortAscBySerialNumber(lessonEntityFromDb).isEmpty())
        {
            final String message = "There are stages in the lesson. "
                    + "You cannot delete a lesson if it contains stages.";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
