package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set;

public class UpdateExerciseRepeatSetContentDto extends UpdateAbstractExerciseSetContentDto
{
    private String repeatCount;

    public String getRepeatCount()
    {
        return repeatCount;
    }

    public void setRepeatCount(String repeatCount)
    {
        this.repeatCount = repeatCount;
    }
}
