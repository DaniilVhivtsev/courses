package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set;

public class ExerciseRepeatSetContent extends AbstractExerciseSetContent
{
    private static final String DISCRIMINATOR = "stage.repeat.exercise.set.content";

    private String repeatCount;

    public String getRepeatCount()
    {
        return repeatCount;
    }

    public ExerciseRepeatSetContent setRepeatCount(String repeatCount)
    {
        this.repeatCount = repeatCount;
        return this;
    }

    @Override
    public String getExerciseSetContentType()
    {
        return DISCRIMINATOR;
    }
}
