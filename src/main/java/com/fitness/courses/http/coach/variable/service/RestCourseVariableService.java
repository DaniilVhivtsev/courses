package com.fitness.courses.http.coach.variable.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.variable.model.dto.ExpressionDto;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionGetResultDto;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionResultDto;
import com.fitness.courses.http.coach.variable.model.dto.ExpressionVariableDto;

public interface RestCourseVariableService
{
    List<ExpressionVariableDto> getExpressionVariables(@NotNull Long courseId,
            @NotNull ExpressionDto expressionDto);

    ExpressionResultDto getExpressionResult(@NotNull Long courseId,
            @NotNull ExpressionGetResultDto expressionGetResultDto);
}
