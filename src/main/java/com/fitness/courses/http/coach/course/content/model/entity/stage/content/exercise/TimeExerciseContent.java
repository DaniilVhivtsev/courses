package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.ExerciseTimeSetContent;

public class TimeExerciseContent extends AbstractExerciseContent<ExerciseTimeSetContent>
{
    private static final String DISCRIMINATOR = "stage.time.exercise.content";

    @Override
    public List<ExerciseTimeSetContent> getSets()
    {
        return super.sets;
    }

    @Override
    public void setSets(List<ExerciseTimeSetContent> sets)
    {
        this.sets = sets;
    }

    @Override
    public String getType()
    {
        return DISCRIMINATOR;
    }
}
