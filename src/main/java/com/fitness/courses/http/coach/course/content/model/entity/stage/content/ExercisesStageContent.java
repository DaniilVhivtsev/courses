package com.fitness.courses.http.coach.course.content.model.entity.stage.content;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.AbstractExerciseContent;

@JsonTypeName("stage.content.exercises")
public class ExercisesStageContent extends AbstractStageContent
{
    private static final String DISCRIMINATOR = "stage.content.exercises";

    private List<AbstractExerciseContent<?>> exercises;

    public List<AbstractExerciseContent<?>> getExercises()
    {
        return exercises;
    }

    public ExercisesStageContent setExercises(List<AbstractExerciseContent<?>> exercises)
    {
        this.exercises = exercises;
        return this;
    }

    @Override
    public String getStageContentType()
    {
        return DISCRIMINATOR;
    }
}
