package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set;

public class ExerciseRepeatSetContent extends AbstractExerciseSetContent
{
    private static final String DISCRIMINATOR = "stage.repeat.exercise.set.content";

    private Integer repeatCount;

    public Integer getRepeatCount()
    {
        return repeatCount;
    }

    public ExerciseRepeatSetContent setRepeatCount(Integer repeatCount)
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
