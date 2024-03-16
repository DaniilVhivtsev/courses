package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set;

public class ExerciseDistanceSetContentInfoDto extends AbstractExerciseSetContentInfoDto
{
    private String distanceKilometers;

    public ExerciseDistanceSetContentInfoDto()
    {
        super.type = ExerciseSetContentType.DISTANCE;
    }

    public String getDistanceKilometers()
    {
        return distanceKilometers;
    }

    public ExerciseDistanceSetContentInfoDto setDistanceKilometers(String distanceKilometers)
    {
        this.distanceKilometers = distanceKilometers;
        return this;
    }
}
