package com.fitness.courses.http.coach.course.content.model.entity.stage;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.entity.stage.exercise.AbstractExerciseContent;

public class ExercisesStageContent extends AbstractStageContent
{
    private static final String DISCRIMINATOR = "stage.content.exercises";

    private List<AbstractExerciseContent<?>> exercises;

    public List<AbstractExerciseContent<?>> getExercises()
    {
        return exercises;
    }

    public void setExercises(List<AbstractExerciseContent<?>> exercises)
    {
        this.exercises = exercises;
    }

    @Override
    public String getType()
    {
        return DISCRIMINATOR;
    }
}
