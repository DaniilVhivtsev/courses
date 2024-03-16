package com.fitness.courses.http.coach.course.model.dto.greeting;

import java.util.List;

public class GreetingUpdateDto
{
    private String title;
    private List<GreetingCourseVariableDto> variableDtoList;

    public String getTitle()
    {
        return title;
    }

    public GreetingUpdateDto setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public List<GreetingCourseVariableDto> getVariableDtoList()
    {
        return variableDtoList;
    }

    public GreetingUpdateDto setVariableDtoList(
            List<GreetingCourseVariableDto> variableDtoList)
    {
        this.variableDtoList = variableDtoList;
        return this;
    }
}
