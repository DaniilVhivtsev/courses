package com.fitness.courses.http.coach.course.content.model.dto.stage;

public class UpdateCourseAuthorStageDto
{
    private Integer serialNumber;
    private String title;

    public Integer getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
