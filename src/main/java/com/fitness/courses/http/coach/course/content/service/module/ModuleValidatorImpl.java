package com.fitness.courses.http.coach.course.content.service.module;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

@Service
public class ModuleValidatorImpl implements ModuleValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(ModuleValidatorImpl.class);

    private static final int MAX_DESCRIPTION_STRING_LENGTH = 256;

    private final ModuleService moduleService;

    @Autowired
    public ModuleValidatorImpl(ModuleService moduleService)
    {
        this.moduleService = moduleService;
    }

    @Override
    public void validateModuleTitle(String title) throws ValidationException
    {
        if (StringUtils.isBlank(title))
        {
            final String message = "Module title is blank";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateModuleDescription(String description) throws ValidationException
    {
        if (StringUtils.isBlank(description))
        {
            final String message = "Module description is blank";
            LOG.error(message);
            throw new ValidationException(message);
        }

        if (description.length() > MAX_DESCRIPTION_STRING_LENGTH)
        {
            final String message =
                    "Module description max string length is %d".formatted(MAX_DESCRIPTION_STRING_LENGTH);
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateModuleBelongsToCourse(@NotNull Long courseId, @NotNull Long moduleId) throws ValidationException
    {
        final ModuleEntity moduleEntityFromDb = moduleService.getOrThrow(moduleId);

        if (!moduleEntityFromDb.getCourse().getId().equals(courseId))
        {
            final String message = "Module with id %d doesn't belong to course with id %d".formatted(moduleId,
                    courseId);
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateExist(final @NotNull Long id) throws ValidationException
    {
        if (moduleService.getOptional(id).isEmpty())
        {
            final String message = "Module not exist";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateSerialNumber(final @NotNull CourseEntity courseEntity, final @NotNull Integer serialNumber)
            throws ValidationException
    {
        if (serialNumber < 0)
        {
            final String message = "Serial number can't be less than 0 (zero)";
            LOG.error(message);
            throw new ValidationException(message);
        }

        Optional<ModuleEntity> moduleEntityOptional =
                moduleService.findAllByCourseAndSortAscBySerialNumber(courseEntity).stream()
                        .reduce((first, second) -> second).stream()
                        .findFirst();

        moduleEntityOptional.ifPresent(moduleEntity ->
        {
            if (serialNumber > moduleEntity.getSerialNumber())
            {
                final String message = "The new serial number is outside the available numbers. "
                        + "It cannot be greater than the serial number of the last module";
                LOG.error(message);
                throw new ValidationException(message);
            }
        });
    }
}
