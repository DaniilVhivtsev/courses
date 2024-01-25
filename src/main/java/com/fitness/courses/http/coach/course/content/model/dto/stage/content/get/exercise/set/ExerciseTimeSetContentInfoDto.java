package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExerciseTimeSetContentInfoDto extends AbstractExerciseSetContentInfoDto
{
    private LocalTime executionTime;

    public ExerciseTimeSetContentInfoDto()
    {
        super.type = ExerciseSetContentType.TIME;
    }

    public LocalTime getExecutionTime()
    {
        return executionTime;
    }

    public void setExecutionTime(LocalTime executionTime)
    {
        this.executionTime = executionTime;
    }
}
