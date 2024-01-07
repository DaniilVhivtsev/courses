package com.fitness.courses.http.student.model.dto.stage.content.exercise;

import java.util.List;

import com.fitness.courses.http.student.model.dto.stage.content.exercise.set.ExerciseDistanceSetContentInfoDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DistanceExerciseContentInfoDto", allOf = AbstractExerciseContentInfoDto.class)
public class DistanceExerciseContentInfoDto extends AbstractExerciseContentInfoDto<ExerciseDistanceSetContentInfoDto>
{
    public DistanceExerciseContentInfoDto()
    {
        super.type = ExerciseContentType.DISTANCE;
    }

    @Override
    public List<ExerciseDistanceSetContentInfoDto> getSets()
    {
        return super.getSets();
    }

    @Override
    public void setSets(List<ExerciseDistanceSetContentInfoDto> sets)
    {
        super.setSets(sets);
    }
}
