package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class UpdateExerciseTimeSetContentDto extends UpdateAbstractExerciseSetContentDto
{
    private LocalTime executionTime;

    public LocalTime getExecutionTime()
    {
        return executionTime;
    }

    public void setExecutionTime(LocalTime executionTime)
    {
        this.executionTime = executionTime;
    }
}
