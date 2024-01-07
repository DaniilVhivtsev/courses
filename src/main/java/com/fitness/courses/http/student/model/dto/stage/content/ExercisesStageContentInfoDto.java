package com.fitness.courses.http.student.model.dto.stage.content;

import java.util.List;

import com.fitness.courses.http.student.model.dto.stage.content.exercise.AbstractExerciseContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.DistanceExerciseContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.RepeatExerciseContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.TimeExerciseContentInfoDto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "ExercisesStageContentInfoDto")
public class ExercisesStageContentInfoDto extends AbstractStageContentInfoDto
{
    @ArraySchema(
            schema = @Schema(
                    oneOf = {
                            DistanceExerciseContentInfoDto.class,
                            RepeatExerciseContentInfoDto.class,
                            TimeExerciseContentInfoDto.class
                    }
            )
    )
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
