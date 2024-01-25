package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateAbstractExerciseContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateDistanceExerciseContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateRepeatExerciseContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateTimeExerciseContentDto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public class UpdateExercisesStageContentDto extends UpdateAbstractStageContentDto
{
    @ArraySchema(schema = @Schema(
            oneOf = {
                    UpdateDistanceExerciseContentDto.class,
                    UpdateRepeatExerciseContentDto.class,
                    UpdateTimeExerciseContentDto.class
            }
    ))
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
