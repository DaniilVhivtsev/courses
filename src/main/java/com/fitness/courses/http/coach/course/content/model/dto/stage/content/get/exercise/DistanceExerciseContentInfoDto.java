package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set.ExerciseDistanceSetContentInfoDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DistanceExerciseContentInfoDtoAuthor", allOf = AbstractExerciseContentInfoDto.class)
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
