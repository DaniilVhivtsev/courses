package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set;

public class ExerciseRepeatSetContentInfoDto extends AbstractExerciseSetContentInfoDto
{
    private String repeatCount;

    public ExerciseRepeatSetContentInfoDto()
    {
        super.type = ExerciseSetContentType.REPEAT;
    }

    public String getRepeatCount()
    {
        return repeatCount;
    }

    public void setRepeatCount(String repeatCount)
    {
        this.repeatCount = repeatCount;
    }
}
