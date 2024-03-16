package com.fitness.courses.http.coach.course.model.dto.greeting;

import com.fitness.courses.http.coach.course.model.dto.CourseVariableTypeEnum;

public class GreetingCourseVariableDto
{
    private String title;
    private String code;
    private CourseVariableTypeEnum type;

    public String getTitle()
    {
        return title;
    }

    public GreetingCourseVariableDto setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getCode()
    {
        return code;
    }

    public GreetingCourseVariableDto setCode(String code)
    {
        this.code = code;
        return this;
    }

    public CourseVariableTypeEnum getType()
    {
        return type;
    }

    public GreetingCourseVariableDto setType(CourseVariableTypeEnum type)
    {
        this.type = type;
        return this;
    }
}
