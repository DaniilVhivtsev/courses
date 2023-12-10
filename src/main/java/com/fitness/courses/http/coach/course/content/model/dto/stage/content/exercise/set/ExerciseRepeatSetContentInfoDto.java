package com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise.set;

public class ExerciseRepeatSetContentInfoDto extends AbstractExerciseSetContentInfoDto
{
    private int repeatCount;

    public ExerciseRepeatSetContentInfoDto()
    {
        super.type = ExerciseSetContentType.REPEAT;
    }

    public int getRepeatCount()
    {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount)
    {
        this.repeatCount = repeatCount;
    }
}
