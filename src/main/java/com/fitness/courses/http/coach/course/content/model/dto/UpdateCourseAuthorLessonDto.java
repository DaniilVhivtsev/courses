package com.fitness.courses.http.coach.course.content.model.dto;

public class UpdateCourseAuthorLessonDto
{
    private String title;

    private Integer serialNumber;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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
