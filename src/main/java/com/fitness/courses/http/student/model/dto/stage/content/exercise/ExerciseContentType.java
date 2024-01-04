package com.fitness.courses.http.student.model.dto.stage.content.exercise;

public enum ExerciseContentType
{
    REPEAT("REPEAT"),
    TIME("TIME"),
    DISTANCE("DISTANCE");

    private final String value;

    ExerciseContentType(String value)
    {
        this.value = value;
    }
}
