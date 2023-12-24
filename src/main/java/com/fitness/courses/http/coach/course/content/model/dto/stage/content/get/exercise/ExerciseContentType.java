package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise;

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
