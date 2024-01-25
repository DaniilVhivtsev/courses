package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.ExerciseDistanceSetContent;

public class DistanceExerciseContent extends AbstractExerciseContent<ExerciseDistanceSetContent>
{
    private static final String DISCRIMINATOR = "stage.distance.exercise.content";

    @Override
    public List<ExerciseDistanceSetContent> getSets()
    {
        return super.sets;
    }

    @Override
    public void setSets(List<ExerciseDistanceSetContent> sets)
    {
        this.sets = sets;
    }

    @Override
    public String getExerciseContentType()
    {
        return DISCRIMINATOR;
    }
}
