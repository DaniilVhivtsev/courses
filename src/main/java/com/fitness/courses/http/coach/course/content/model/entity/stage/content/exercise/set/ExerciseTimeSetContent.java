package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExerciseTimeSetContent extends AbstractExerciseSetContent
{
    private static final String DISCRIMINATOR = "stage.time.exercise.set.content";

    private LocalTime executionTime;

    public LocalTime getExecutionTime()
    {
        return executionTime;
    }

    public void setExecutionTime(LocalTime executionTime)
    {
        this.executionTime = executionTime;
    }

    @Override
    public String getType()
    {
        return null;
    }
}
