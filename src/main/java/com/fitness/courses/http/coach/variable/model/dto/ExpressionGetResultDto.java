package com.fitness.courses.http.coach.variable.model.dto;

import java.util.List;

import com.fitness.courses.http.coach.course.model.dto.CourseVariableTypeEnum;

public class ExpressionGetResultDto
{
    private String expression;
    private CourseVariableTypeEnum resultType;
    private List<ExpressionVariableWithResultDto> variables;

    public String getExpression()
    {
        return expression;
    }

    public void setExpression(String expression)
    {
        this.expression = expression;
    }

    public CourseVariableTypeEnum getResultType()
    {
        return resultType;
    }

    public void setResultType(CourseVariableTypeEnum resultType)
    {
        this.resultType = resultType;
    }

    public List<ExpressionVariableWithResultDto> getVariables()
    {
        return variables;
    }

    public void setVariables(
            List<ExpressionVariableWithResultDto> variables)
    {
        this.variables = variables;
    }
}
