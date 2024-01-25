package com.fitness.courses.http.coach.course.content.model.dto.module;

import java.util.ArrayList;
import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.lesson.CourseAuthorLessonInfo;

public class CourseAuthorModuleInfo
{
    private Long id;

    private String title;

    private int serialNumber;

    private String description;

    private List<CourseAuthorLessonInfo> lessons = new ArrayList<>();

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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<CourseAuthorLessonInfo> getLessons()
    {
        return lessons;
    }

    public void setLessons(List<CourseAuthorLessonInfo> lessons)
    {
        this.lessons = lessons;
    }
}
