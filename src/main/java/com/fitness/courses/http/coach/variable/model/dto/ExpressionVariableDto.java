package com.fitness.courses.http.coach.variable.model.dto;

import com.fitness.courses.http.coach.variable.model.entity.VariableTypeEnum;

public class ExpressionVariableDto
{
    private String code;
    private String title;
    private VariableTypeEnum type;

    public String getCode()
    {
        return code;
    }

    public ExpressionVariableDto setCode(String code)
    {
        this.code = code;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public ExpressionVariableDto setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public VariableTypeEnum getType()
    {
        return type;
    }

    public ExpressionVariableDto setType(VariableTypeEnum type)
    {
        this.type = type;
        return this;
    }
}
