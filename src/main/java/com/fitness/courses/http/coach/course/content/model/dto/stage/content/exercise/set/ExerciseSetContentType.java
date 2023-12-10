package com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise.set;

public enum ExerciseSetContentType
{
    REPEAT("REPEAT"),
    TIME("TIME");

    private final String value;

    ExerciseSetContentType(String value)
    {
        this.value = value;
    }
}