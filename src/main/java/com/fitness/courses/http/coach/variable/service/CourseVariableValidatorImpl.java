package com.fitness.courses.http.coach.variable.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.coach.course.model.dto.greeting.GreetingCourseVariableDto;
import com.fitness.courses.http.coach.variable.model.entity.CourseVariableEntity;

@Service
public class CourseVariableValidatorImpl implements CourseVariableValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(CourseVariableValidatorImpl.class);

    private final CourseVariableService courseVariableService;

    public CourseVariableValidatorImpl(CourseVariableService courseVariableService)
    {
        this.courseVariableService = courseVariableService;
    }

    @Override
    public void updateCourseVariables(Long courseId, List<GreetingCourseVariableDto> updatedVariableDtoList)
    {
        updatedVariableDtoList.forEach(this::validateFieldsContent);

        // Проверить, что отсутствуют переменный с одинаковым кодом
        if (updatedVariableDtoList.stream()
                .map(GreetingCourseVariableDto::getCode)
                .distinct()
                .count() != updatedVariableDtoList.size())
        {
            final String message = "Variable collection contains variables with the same code";
            LOG.error(message);
            throw new ValidationException(message);
        }

        // 1. Проверить, если есть данная переменная, то если у нее изменился тип, то проверить, что отсутсует данная
        // перемнная в контенте, чтобы не было проблем с тем какие значения ввели студенты.

        // 2. Проверить какие переменные были удалены, если были удалены переменные, которые используются, то нужно
        // выдать ошибку.

        final Map<String, CourseVariableEntity> codeCourseVariablesWithEntity =
                courseVariableService.findAllByCourseId(courseId).stream()
                        .collect(Collectors.toMap(CourseVariableEntity::getCode, e -> e));

        /*updatedVariableDtoList.forEach(updatedVariable ->
        {
            final CourseVariableEntity courseVariableFromDB = codeCourseVariablesWithEntity.get(
                    updatedVariable.getCode());

            if (courseVariableFromDB == null)
            {
                return;
            }

            if (!updatedVariable.getType().name().equals(courseVariableFromDB.getType().name()))
            {
                if (!courseVariableService.findAllStageUuidsWithVariableInCourse(courseId, updatedVariable.getCode())
                        .isEmpty())
                {
                    final String message = "Can't update type in variable if course stages has this variable.";
                    LOG.error(message);

                    throw new ValidationException(message);
                }
            }
        });*/

        updatedVariableDtoList.stream()
                .map(GreetingCourseVariableDto::getCode)
                .forEach(codeCourseVariablesWithEntity::remove);

        codeCourseVariablesWithEntity.values().forEach(variable ->
        {
            if (!courseVariableService.findAllStageUuidsWithVariableInCourse(courseId, variable.getCode())
                    .isEmpty())
            {
                final String message = "Can't remove variable if course stages has this variable.";
                LOG.error(message);

                throw new ValidationException(message);
            }
        });
    }

    private void validateFieldsContent(GreetingCourseVariableDto variableDto)
    {
        if (StringUtils.isBlank(variableDto.getTitle()))
        {
            final String message = "Variable title is blank";
            LOG.error(message);
            throw new ValidationException(message);
        }

        if (StringUtils.isBlank(variableDto.getCode()))
        {
            final String message = "Variable code is blank";
            LOG.error(message);
            throw new ValidationException(message);
        }

        if (variableDto.getType() == null)
        {
            final String message = "Variable type is null";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
