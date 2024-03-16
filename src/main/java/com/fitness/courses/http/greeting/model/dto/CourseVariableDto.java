package com.fitness.courses.http.greeting.model.dto;

import com.fitness.courses.http.coach.course.model.dto.CourseVariableTypeEnum;

public class CourseVariableDto
{
    private String code;
    private String title;
    private CourseVariableTypeEnum type;

    public String getCode()
    {
        return code;
    }

    public CourseVariableDto setCode(String code)
    {
        this.code = code;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public CourseVariableDto setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public CourseVariableTypeEnum getType()
    {
        return type;
    }

    public CourseVariableDto setType(CourseVariableTypeEnum type)
    {
        this.type = type;
        return this;
    }
}
