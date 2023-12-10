package com.fitness.courses.http.coach.course.content.model.entity.stage.exercise.set;

public class ExerciseRepeatSetContent extends AbstractExerciseSetContent
{
    private static final String DISCRIMINATOR = "stage.repeat.exercise.set.content";

    private int repeatCount;

    public int getRepeatCount()
    {
        return repeatCount;
    }

    public ExerciseRepeatSetContent setRepeatCount(int repeatCount)
    {
        this.repeatCount = repeatCount;
        return this;
    }

    @Override
    public String getType()
    {
        return DISCRIMINATOR;
    }
}
