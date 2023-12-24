package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set;

public class ExerciseDistanceSetContentInfoDto extends AbstractExerciseSetContentInfoDto
{
    private Float distanceKilometers;

    public ExerciseDistanceSetContentInfoDto()
    {
        super.type = ExerciseSetContentType.DISTANCE;
    }

    public Float getDistanceKilometers()
    {
        return distanceKilometers;
    }

    public ExerciseDistanceSetContentInfoDto setDistanceKilometers(Float distanceKilometers)
    {
        this.distanceKilometers = distanceKilometers;
        return this;
    }
}
