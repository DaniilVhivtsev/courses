package com.fitness.courses.http.coach.course.content.model.entity.stage.exercise;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.entity.stage.exercise.set.ExerciseRepeatSetContent;

public class RepeatExerciseContent extends AbstractExerciseContent<ExerciseRepeatSetContent>
{
    private static final String DISCRIMINATOR = "stage.repeat.exercise.content";

    @Override
    public List<ExerciseRepeatSetContent> getSets()
    {
        return super.sets;
    }

    @Override
    public void setSets(List<ExerciseRepeatSetContent> sets)
    {
        this.sets = sets;
    }

    @Override
    public String getType()
    {
        return DISCRIMINATOR;
    }
}
