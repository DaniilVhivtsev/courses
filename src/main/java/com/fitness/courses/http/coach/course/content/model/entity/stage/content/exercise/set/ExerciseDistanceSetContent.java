package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set;

public class ExerciseDistanceSetContent extends AbstractExerciseSetContent
{
    private static final String DISCRIMINATOR = "stage.distance.exercise.set.content";

    private String distanceKilometers;

    public String getDistanceKilometers()
    {
        return distanceKilometers;
    }

    public ExerciseDistanceSetContent setDistanceKilometers(String distanceKilometers)
    {
        this.distanceKilometers = distanceKilometers;
        return this;
    }

    @Override
    public String getExerciseSetContentType()
    {
        return DISCRIMINATOR;
    }
}
