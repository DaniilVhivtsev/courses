package com.fitness.courses.http.coach.course.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Dto с информацией для создания нового курса")
public class NewCourseDto
{
    @Schema(description = "Заголовок курса.")
    private String title;

    public String getTitle()
    {
        return title;
    }

    public NewCourseDto setTitle(String title)
    {
        this.title = title;
        return this;
    }
}
