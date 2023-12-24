package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateAbstractExerciseContentDto;

public class UpdateExercisesStageContentDto extends UpdateAbstractStageContentDto
{
    private List<? extends UpdateAbstractExerciseContentDto<?>> exercises;

    public List<? extends UpdateAbstractExerciseContentDto<?>> getExercises()
    {
        return exercises;
    }

    public void setExercises(
            List<? extends UpdateAbstractExerciseContentDto<?>> exercises)
    {
        this.exercises = exercises;
    }
}
