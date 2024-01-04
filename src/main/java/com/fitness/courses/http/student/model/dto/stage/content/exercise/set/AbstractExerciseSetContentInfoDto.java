package com.fitness.courses.http.student.model.dto.stage.content.exercise.set;

import java.time.LocalTime;

public abstract class AbstractExerciseSetContentInfoDto
{
    private String uuid;

    private Float countOfKilograms;

    private LocalTime pauseAfter;

    protected ExerciseSetContentType type;

    private boolean isCompleted;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public Float getCountOfKilograms()
    {
        return countOfKilograms;
    }

    public void setCountOfKilograms(Float countOfKilograms)
    {
        this.countOfKilograms = countOfKilograms;
    }

    public LocalTime getPauseAfter()
    {
        return pauseAfter;
    }

    public void setPauseAfter(LocalTime pauseAfter)
    {
        this.pauseAfter = pauseAfter;
    }

    public ExerciseSetContentType getType()
    {
        return type;
    }

    protected void setType(
            ExerciseSetContentType type)
    {
        this.type = type;
    }

    public boolean isCompleted()
    {
        return isCompleted;
    }

    public void setCompleted(boolean completed)
    {
        isCompleted = completed;
    }
}
