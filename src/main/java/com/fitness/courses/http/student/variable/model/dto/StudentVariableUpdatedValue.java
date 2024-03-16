package com.fitness.courses.http.student.variable.model.dto;

public class StudentVariableUpdatedValue
{
    private Long id;
    private Float value;

    public Long getId()
    {
        return id;
    }

    public StudentVariableUpdatedValue setId(Long id)
    {
        this.id = id;
        return this;
    }

    public Float getValue()
    {
        return value;
    }

    public StudentVariableUpdatedValue setValue(Float value)
    {
        this.value = value;
        return this;
    }
}
