package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set;

public class UpdateExerciseDistanceSetContentDto extends UpdateAbstractExerciseSetContentDto
{
    private String distanceKilometers;

    public String getDistanceKilometers()
    {
        return distanceKilometers;
    }

    public void setDistanceKilometers(String distanceKilometers)
    {
        this.distanceKilometers = distanceKilometers;
    }
}
