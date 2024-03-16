package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set;

import java.time.LocalTime;

public class ExerciseTimeSetContent extends AbstractExerciseSetContent
{
    private static final String DISCRIMINATOR = "stage.time.exercise.set.content";

    private String executionTime;

    public String getExecutionTime()
    {
        return executionTime;
    }

    public void setExecutionTime(String executionTime)
    {
        this.executionTime = executionTime;
    }

    @Override
    public String getExerciseSetContentType()
    {
        return DISCRIMINATOR;
    }
}
