package com.fitness.courses.http.coach.course.content.service.stage;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;

@Service
public class StageValidatorImpl implements StageValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(StageValidatorImpl.class);

    private final StageService stageService;

    @Autowired
    public StageValidatorImpl(StageService stageService)
    {
        this.stageService = stageService;
    }

    @Override
    public void validateExist(final @NotNull Long id) throws ValidationException
    {
        if (stageService.getOptional(id).isEmpty())
        {
            final String message = "Stage not exist";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateStageBelongsToLesson(@NotNull Long lessonId, @NotNull Long stageId)
    {
        final StageEntity stageEntityFromDb = stageService.getOrThrow(stageId);

        if (!stageEntityFromDb.getLesson().getId().equals(lessonId))
        {
            final String message = "Stage with id %d doesn't belong to lesson with id %d".formatted(lessonId,
                    stageId);
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateSerialNumber(final @NotNull LessonEntity lessonEntity, final @NotNull Integer serialNumber)
            throws ValidationException
    {
        if (serialNumber < 0)
        {
            final String message = "Serial number can't be less than 0 (zero)";
            LOG.error(message);
            throw new ValidationException(message);
        }

        Optional<StageEntity> stageEntityOptional =
                stageService.findAllByLessonAndSortAscBySerialNumber(lessonEntity).stream()
                        .reduce((first, second) -> second).stream()
                        .findFirst();

        stageEntityOptional.ifPresent(stageEntity ->
        {
            if (serialNumber > stageEntity.getSerialNumber())
            {
                final String message = "The new serial number is outside the available numbers. "
                        + "It cannot be greater than the serial number of the last stage";
                LOG.error(message);
                throw new ValidationException(message);
            }
        });
    }
}
