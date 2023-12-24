package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set.ExerciseRepeatSetContentInfoDto;

public class RepeatExerciseContentInfoDto extends AbstractExerciseContentInfoDto<ExerciseRepeatSetContentInfoDto>
{
    public RepeatExerciseContentInfoDto()
    {
        super.type = ExerciseContentType.REPEAT;
    }

    @Override
    public List<ExerciseRepeatSetContentInfoDto> getSets()
    {
        return super.getSets();
    }

    @Override
    public void setSets(List<ExerciseRepeatSetContentInfoDto> sets)
    {
        super.setSets(sets);
    }
}
