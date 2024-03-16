package com.fitness.courses.http.coach.variable.service;

import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import com.fitness.courses.http.coach.course.model.dto.CourseVariableTypeEnum;
import com.fitness.courses.http.coach.course.service.CourseValidator;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionDto;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionGetResultDto;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionResultDto;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionVariableDto;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionVariableWithResultDto;
import com.fitness.courses.http.coach.variable.model.entity.CourseVariableEntity;

@Service
public class RestCourseVariableServiceImpl implements RestCourseVariableService
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(RestCourseVariableServiceImpl.class);

    private final CourseVariableService courseVariableService;
    private final CourseValidator courseValidator;

    @Autowired
    public RestCourseVariableServiceImpl(
            CourseVariableService courseVariableService,
            CourseValidator courseValidator)
    {
        this.courseVariableService = courseVariableService;
        this.courseValidator = courseValidator;
    }

    @Override
    public List<ExpressionVariableDto> getExpressionVariables(@NotNull Long courseId,
            @NotNull ExpressionDto expressionDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        final List<CourseVariableEntity> courseVariables = courseVariableService.findAllByCourseId(courseId);
        final String expression = expressionDto.getExpression();

        return courseVariables.stream()
                .filter(courseVariable -> expression.contains(courseVariable.getCode()))
                .map(courseVariable -> new ExpressionVariableDto()
                        .setCode(courseVariable.getCode())
                        .setTitle(courseVariable.getTitle())
                        .setType(courseVariable.getType()))
                .toList();
    }

    @Override
    public ExpressionResultDto getExpressionResult(@NotNull Long courseId,
            @NotNull ExpressionGetResultDto expressionGetResultDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        return new ExpressionResultDto()
                .setResult(
                        getSetFieldResultByVariables(expressionGetResultDto.getExpression(),
                                expressionGetResultDto.getResultType(),
                                expressionGetResultDto.getVariables()));
    }

    private String getSetFieldResultByVariables(String expression, @NotNull CourseVariableTypeEnum fieldType,
            @NotNull List<ExpressionVariableWithResultDto> courseVariablesWithStudentValuesInfo)
    {
        if (StringUtils.isBlank(expression))
        {
            return getResultByType(0.0F, fieldType).toString();
        }
        if (NumberUtils.isCreatable(expression))
        {
            return getResultByType(Float.valueOf(expression), fieldType).toString();
        }

        try
        {
            DoubleEvaluator eval = new DoubleEvaluator();
            StaticVariableSet<Double> variables = new StaticVariableSet<Double>();

            courseVariablesWithStudentValuesInfo.forEach(courseVariableWithStudentValueInfo ->
            {
                double studentResult;
                if (courseVariableWithStudentValueInfo.getValue() == null)
                {
                    studentResult = 1;
                }
                else
                {
                    studentResult = (double)courseVariableWithStudentValueInfo.getValue();
                }

                variables.set(courseVariableWithStudentValueInfo.getCode(), studentResult);
            });

            Double expressionResult = eval.evaluate(expression, variables);
            return getResultByType(expressionResult.floatValue(), fieldType).toString();
        }
        catch (Exception e)
        {
            String message = "Error while creating result by expression: " + expression;
            LOG.error(message, e);

            return getResultByType(0.0F, fieldType).toString();
        }
    }

    private Object getResultByType(Float result, @NotNull CourseVariableTypeEnum fieldType)
    {
        return switch (fieldType)
                {
                    case INTEGER -> result.intValue();
                    case FLOAT -> (float)(Math.floor(result * 1000) / 1000.0);
                    case TIME -> {
                        final long secondsSource = result.longValue();
                        int hours = (int)(secondsSource / 3600);
                        int minutes = (int)((secondsSource % 3600) / 60);
                        int secondsd = (int)(secondsSource % 60);

                        if (hours >= 24)
                        {
                            hours %= 24;
                        }

                        yield LocalTime.of(hours, minutes, secondsd);
                    }
                };
    }
}
