package com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise;

public enum ExerciseContentType
{
    REPEAT("REPEAT"),
    TIME("TIME");

    private final String value;

    ExerciseContentType(String value)
    {
        this.value = value;
    }
}
