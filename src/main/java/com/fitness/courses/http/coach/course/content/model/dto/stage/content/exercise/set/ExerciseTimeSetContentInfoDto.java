package com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise.set;

import java.time.LocalDateTime;

public class ExerciseTimeSetContentInfoDto extends AbstractExerciseSetContentInfoDto
{
    private LocalDateTime executionTime;

    public ExerciseTimeSetContentInfoDto()
    {
        super.type = ExerciseSetContentType.TIME;
    }

    public LocalDateTime getExecutionTime()
    {
        return executionTime;
    }

    public void setExecutionTime(LocalDateTime executionTime)
    {
        this.executionTime = executionTime;
    }
}
