package com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise.set;

import java.time.LocalDateTime;

public abstract class AbstractExerciseSetContentInfoDto
{
    private String uuid;

    private Float countOfKilograms;

    private LocalDateTime pauseAfter;

    protected ExerciseSetContentType type;

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

    public LocalDateTime getPauseAfter()
    {
        return pauseAfter;
    }

    public void setPauseAfter(LocalDateTime pauseAfter)
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
}
