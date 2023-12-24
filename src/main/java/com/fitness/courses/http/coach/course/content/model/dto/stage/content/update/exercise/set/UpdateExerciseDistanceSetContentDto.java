package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set;

public class UpdateExerciseDistanceSetContentDto extends UpdateAbstractExerciseSetContentDto
{
    private Float distanceKilometers;

    public Float getDistanceKilometers()
    {
        return distanceKilometers;
    }

    public void setDistanceKilometers(Float distanceKilometers)
    {
        this.distanceKilometers = distanceKilometers;
    }
}
