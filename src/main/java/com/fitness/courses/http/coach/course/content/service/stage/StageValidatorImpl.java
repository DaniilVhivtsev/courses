package com.fitness.courses.http.coach.course.content.service.stage;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.AbstractStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.ExercisesStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.AbstractExerciseContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.AbstractExerciseSetContent;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

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
            final String message = "Stage with id %d doesn't belong to lesson with id %d".formatted(stageId, lessonId);
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateStageBelongsToCourse(@NotNull Long courseId, @NotNull Long stageId)
    {
        final StageEntity stageEntityFromDb = stageService.getOrThrow(stageId);
        final CourseEntity courseEntity = stageEntityFromDb.getLesson().getModule().getCourse();

        if (!courseEntity.getId().equals(courseId))
        {
            final String message = "Stage with id %d doesn't belong to course with id %d".formatted(stageId, courseId);
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

    @Override
    public void validateStageContentExist(final @NotNull Long stageId, final @NotNull String stageContentUuid)
            throws ValidationException
    {
        final StageEntity stageEntityFromDb = stageService.getOrThrow(stageId);
        if (stageEntityFromDb.getStageContent().stream()
                .noneMatch(stageContent -> stageContent.getUuid().equals(stageContentUuid)))
        {
            final String message = "Stage content not exist";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateContentSerialNumber(final @NotNull Long stageId, final @NotNull Integer contentSerialNumber)
            throws ValidationException
    {
        if (contentSerialNumber < 0)
        {
            final String message = "Serial number can't be less than 0 (zero)";
            LOG.error(message);
            throw new ValidationException(message);
        }

        StageEntity stageEntity = stageService.getOrThrow(stageId);

        List<AbstractStageContent> contentList = stageEntity.getStageContent();

        AbstractStageContent lastBySerialStageContent = contentList.get(contentList.size() - 1);

        if (contentSerialNumber > lastBySerialStageContent.getSerialNumber())
        {
            final String message = "The new serial number is outside the available numbers. "
                    + "It cannot be greater than the serial number of the last stage content";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateExerciseContentExist(final @NotNull Long stageId, final @NotNull String stageContentUuid,
            final @NotNull String stageContentExerciseUuid) throws ValidationException
    {
        final StageEntity stageEntityFromDb = stageService.getOrThrow(stageId);

        validateStageContentExist(stageId, stageContentUuid);

        ExercisesStageContent exercisesStageContent = (ExercisesStageContent)stageEntityFromDb.getStageContent()
                .stream()
                .filter(stageContent -> stageContent.getUuid().equals(stageContentUuid))
                .findFirst()
                .orElseThrow();

        if (exercisesStageContent.getExercises()
                .stream()
                .noneMatch(exercise -> exercise.getUuid().equals(stageContentExerciseUuid)))
        {
            final String message = "Stage exercise content not exist";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateExerciseSetContentExist(final @NotNull Long stageId, final @NotNull String stageContentUuid,
            final @NotNull String stageContentExerciseUuid, final @NotNull String stageExerciseSetContentUuid)
    {
        final StageEntity stageEntityFromDb = stageService.getOrThrow(stageId);

        validateStageContentExist(stageId, stageContentUuid);

        validateExerciseContentExist(stageId, stageContentUuid, stageContentExerciseUuid);

        AbstractExerciseContent<?> exerciseContent = ((ExercisesStageContent)stageEntityFromDb.getStageContent()
                .stream()
                .filter(stageContent -> stageContent.getUuid().equals(stageContentUuid))
                .findFirst()
                .orElseThrow())
                .getExercises()
                .stream()
                .filter(exercise -> exercise.getUuid().equals(stageContentExerciseUuid))
                .findFirst()
                .orElseThrow();

        if (exerciseContent.getSets()
                .stream()
                .noneMatch(set -> set.getUuid().equals(stageExerciseSetContentUuid)))
        {
            final String message = "Stage exercise set content not exist";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
