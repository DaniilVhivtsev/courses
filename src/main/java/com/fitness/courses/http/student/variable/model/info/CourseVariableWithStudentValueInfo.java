package com.fitness.courses.http.student.variable.model.info;

import com.fitness.courses.http.coach.course.model.dto.CourseVariableTypeEnum;

public class CourseVariableWithStudentValueInfo
{
    private Long id;
    private String title;
    private String code;
    private CourseVariableTypeEnum type;
    private Float studentValue;

    public Long getId()
    {
        return id;
    }

    public CourseVariableWithStudentValueInfo setId(Long id)
    {
        this.id = id;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public CourseVariableWithStudentValueInfo setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getCode()
    {
        return code;
    }

    public CourseVariableWithStudentValueInfo setCode(String code)
    {
        this.code = code;
        return this;
    }

    public CourseVariableTypeEnum getType()
    {
        return type;
    }

    public CourseVariableWithStudentValueInfo setType(CourseVariableTypeEnum type)
    {
        this.type = type;
        return this;
    }

    public Float getStudentValue()
    {
        return studentValue;
    }

    public CourseVariableWithStudentValueInfo setStudentValue(Float studentValue)
    {
        this.studentValue = studentValue;
        return this;
    }
}
