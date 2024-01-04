package com.fitness.courses.http.catalog.model.dto.content;

import java.util.List;

public class ModuleInfoDto
{
    private Long id;

    private String title;

    private int serialNumber;

    private List<LessonInfoDto> lessons;

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

    public List<LessonInfoDto> getLessons()
    {
        return lessons;
    }

    public void setLessons(List<LessonInfoDto> lessons)
    {
        this.lessons = lessons;
    }
}
