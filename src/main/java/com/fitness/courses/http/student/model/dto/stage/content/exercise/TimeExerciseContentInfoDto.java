package com.fitness.courses.http.student.model.dto.stage.content.exercise;

import java.util.List;

import com.fitness.courses.http.student.model.dto.stage.content.exercise.set.ExerciseTimeSetContentInfoDto;

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
