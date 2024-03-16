package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set;

public class UpdateExerciseTimeSetContentDto extends UpdateAbstractExerciseSetContentDto
{
    private String executionTime;

    public String getExecutionTime()
    {
        return executionTime;
    }

    public void setExecutionTime(String executionTime)
    {
        this.executionTime = executionTime;
    }
}
