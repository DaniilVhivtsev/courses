package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set;

public class UpdateExerciseRepeatSetContentDto extends UpdateAbstractExerciseSetContentDto
{
    private Integer repeatCount;

    public Integer getRepeatCount()
    {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount)
    {
        this.repeatCount = repeatCount;
    }
}
