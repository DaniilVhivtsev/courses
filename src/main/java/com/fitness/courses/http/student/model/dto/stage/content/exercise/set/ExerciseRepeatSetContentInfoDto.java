package com.fitness.courses.http.student.model.dto.stage.content.exercise.set;

public class ExerciseRepeatSetContentInfoDto extends AbstractExerciseSetContentInfoDto
{
    private Integer repeatCount;

    public ExerciseRepeatSetContentInfoDto()
    {
        super.type = ExerciseSetContentType.REPEAT;
    }

    public Integer getRepeatCount()
    {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount)
    {
        this.repeatCount = repeatCount;
    }
}
