package com.fitness.courses.http.coach.course.content.model.dto.stage;

public class CourseAuthorStageInfoDto
{
    private Long id;

    private int serialNumber;

    private String title;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber)
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
