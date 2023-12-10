package com.fitness.courses.http.coach.course.content.model.dto.stage.content;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise.AbstractExerciseContentInfoDto;

public class ExercisesStageContentInfoDto extends AbstractStageContentInfoDto
{
    private List<? extends AbstractExerciseContentInfoDto<?>> exercises;

    public ExercisesStageContentInfoDto()
    {
        super.type = StageContentType.EXERCISES;
    }

    public List<? extends AbstractExerciseContentInfoDto<?>> getExercises()
    {
        return exercises;
    }

    public void setExercises(
            List<? extends AbstractExerciseContentInfoDto<?>> exercises)
    {
        this.exercises = exercises;
    }
}
