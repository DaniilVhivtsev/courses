package com.fitness.courses.http.greeting.model.dto;

import java.util.List;

public class GreetingContent
{
    private String courseGreetingTitle;
    private List<CourseVariableDto> variables;

    public String getCourseGreetingTitle()
    {
        return courseGreetingTitle;
    }

    public GreetingContent setCourseGreetingTitle(String courseGreetingTitle)
    {
        this.courseGreetingTitle = courseGreetingTitle;
        return this;
    }

    public List<CourseVariableDto> getVariables()
    {
        return variables;
    }

    public GreetingContent setVariables(List<CourseVariableDto> variables)
    {
        this.variables = variables;
        return this;
    }
}
