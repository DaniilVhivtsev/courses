package com.fitness.courses.http.student.variable.model.dto;

import com.fitness.courses.http.coach.course.model.dto.CourseVariableTypeEnum;

public class CourseVariableWithStudentValue
{
    private Long id;
    private String title;
    private CourseVariableTypeEnum type;
    private Float studentValue;

    public Long getId()
    {
        return id;
    }

    public CourseVariableWithStudentValue setId(Long id)
    {
        this.id = id;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public CourseVariableWithStudentValue setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public CourseVariableTypeEnum getType()
    {
        return type;
    }

    public CourseVariableWithStudentValue setType(CourseVariableTypeEnum type)
    {
        this.type = type;
        return this;
    }

    public Float getStudentValue()
    {
        return studentValue;
    }

    public CourseVariableWithStudentValue setStudentValue(Float studentValue)
    {
        this.studentValue = studentValue;
        return this;
    }
}
