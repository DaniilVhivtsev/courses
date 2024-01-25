package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.AbstractExerciseContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.DistanceExerciseContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.RepeatExerciseContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.TimeExerciseContentInfoDto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "ExercisesStageContentInfoDtoAuthor")
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
