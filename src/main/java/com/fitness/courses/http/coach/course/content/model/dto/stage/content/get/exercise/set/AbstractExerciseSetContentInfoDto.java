package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set;

import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class AbstractExerciseSetContentInfoDto
{
    private String uuid;

    private String countOfKilograms;

    private LocalTime pauseAfter;

    protected ExerciseSetContentType type;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getCountOfKilograms()
    {
        return countOfKilograms;
    }

    public void setCountOfKilograms(String countOfKilograms)
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
}
