package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set.ExerciseTimeSetContentInfoDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "TimeExerciseContentInfoDtoAuthor", allOf = AbstractExerciseContentInfoDto.class)
public class TimeExerciseContentInfoDto extends AbstractExerciseContentInfoDto<ExerciseTimeSetContentInfoDto>
{
    public TimeExerciseContentInfoDto()
    {
        super.type = ExerciseContentType.TIME;
    }

    @Override
    public List<ExerciseTimeSetContentInfoDto> getSets()
    {
        return super.getSets();
    }

    @Override
    public void setSets(List<ExerciseTimeSetContentInfoDto> sets)
    {
        super.setSets(sets);
    }
}
