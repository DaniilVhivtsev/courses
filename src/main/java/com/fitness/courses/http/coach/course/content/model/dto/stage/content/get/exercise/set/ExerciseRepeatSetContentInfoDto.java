package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set;

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
