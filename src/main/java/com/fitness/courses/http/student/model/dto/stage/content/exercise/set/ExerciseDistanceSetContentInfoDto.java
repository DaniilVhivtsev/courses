package com.fitness.courses.http.student.model.dto.stage.content.exercise.set;

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
