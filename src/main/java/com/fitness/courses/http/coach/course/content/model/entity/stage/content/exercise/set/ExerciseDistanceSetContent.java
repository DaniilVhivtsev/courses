package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set;

public class ExerciseDistanceSetContent extends AbstractExerciseSetContent
{
    private static final String DISCRIMINATOR = "stage.distance.exercise.set.content";

    private Float distanceKilometers;

    public Float getDistanceKilometers()
    {
        return distanceKilometers;
    }

    public ExerciseDistanceSetContent setDistanceKilometers(Float distanceKilometers)
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
