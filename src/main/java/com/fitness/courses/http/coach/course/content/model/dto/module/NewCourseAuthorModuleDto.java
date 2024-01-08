package com.fitness.courses.http.coach.course.content.model.dto.module;

public class NewCourseAuthorModuleDto
{
    private String title;

    private String description;

    public String getTitle()
    {
        return title;
    }

    public NewCourseAuthorModuleDto setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getDescription()
    {
        return description;
    }

    public NewCourseAuthorModuleDto setDescription(String description)
    {
        this.description = description;
        return this;
    }
}
