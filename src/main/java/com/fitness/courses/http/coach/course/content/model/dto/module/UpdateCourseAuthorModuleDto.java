package com.fitness.courses.http.coach.course.content.model.dto.module;

public class UpdateCourseAuthorModuleDto
{
    private String title;

    private String description;

    private Integer serialNumber;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber)
    {
        this.serialNumber = serialNumber;
    }
}
