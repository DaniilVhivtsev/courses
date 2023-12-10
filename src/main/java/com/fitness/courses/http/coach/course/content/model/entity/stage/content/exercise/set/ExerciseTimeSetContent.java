package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set;

import java.time.LocalDateTime;

public class ExerciseTimeSetContent extends AbstractExerciseSetContent
{
    private static final String DISCRIMINATOR = "stage.time.exercise.set.content";

    private LocalDateTime executionTime;

    public LocalDateTime getExecutionTime()
    {
        return executionTime;
    }

    public void setExecutionTime(LocalDateTime executionTime)
    {
        this.executionTime = executionTime;
    }

    @Override
    public String getType()
    {
        return null;
    }
}
