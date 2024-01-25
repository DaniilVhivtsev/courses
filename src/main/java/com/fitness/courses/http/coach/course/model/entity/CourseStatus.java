package com.fitness.courses.http.coach.course.model.entity;

public enum CourseStatus
{
    DRAFT("DRAFT"),
    PUBLISHED("PUBLISHED");

    private final String value;

    CourseStatus(String value)
    {
        this.value = value;
    }
}
