package com.fitness.courses.http.student.model.dto.stage.content.exercise.set;

public enum ExerciseSetContentType
{
    REPEAT("REPEAT"),
    TIME("TIME"),
    DISTANCE("DISTANCE");

    private final String value;

    ExerciseSetContentType(String value)
    {
        this.value = value;
    }
}
