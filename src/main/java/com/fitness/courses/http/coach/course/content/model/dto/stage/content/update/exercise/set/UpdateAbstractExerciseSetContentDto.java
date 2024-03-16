package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set;

import java.time.LocalTime;

public abstract class UpdateAbstractExerciseSetContentDto
{
    private String uuid;

    private String countOfKilograms;

    private LocalTime pauseAfter;

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
}
