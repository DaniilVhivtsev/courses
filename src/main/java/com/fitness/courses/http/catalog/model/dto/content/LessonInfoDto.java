package com.fitness.courses.http.catalog.model.dto.content;

public class LessonInfoDto
{
    private Long id;

    private String title;

    private int serialNumber;

    private int stagesNumber;

    private int completedStagesCount;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public int getStagesNumber()
    {
        return stagesNumber;
    }

    public void setStagesNumber(int stagesNumber)
    {
        this.stagesNumber = stagesNumber;
    }

    public int getCompletedStagesCount()
    {
        return completedStagesCount;
    }

    public void setCompletedStagesCount(int completedStagesCount)
    {
        this.completedStagesCount = completedStagesCount;
    }
}
