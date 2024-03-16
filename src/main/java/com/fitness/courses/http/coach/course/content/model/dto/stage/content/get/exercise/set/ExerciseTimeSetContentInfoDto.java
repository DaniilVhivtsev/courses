package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ExerciseTimeSetContentInfoDto extends AbstractExerciseSetContentInfoDto
{
    private String executionTime;

    public ExerciseTimeSetContentInfoDto()
    {
        super.type = ExerciseSetContentType.TIME;
    }

    public String getExecutionTime()
    {
        return executionTime;
    }

    public void setExecutionTime(String executionTime)
    {
        this.executionTime = executionTime;
    }
}
