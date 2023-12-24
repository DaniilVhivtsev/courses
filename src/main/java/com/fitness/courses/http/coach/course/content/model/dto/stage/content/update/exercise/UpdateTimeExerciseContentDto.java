package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseTimeSetContentDto;

public class UpdateTimeExerciseContentDto extends UpdateAbstractExerciseContentDto<UpdateExerciseTimeSetContentDto>
{
    @Override
    public List<UpdateExerciseTimeSetContentDto> getSets()
    {
        return super.getSets();
    }

    @Override
    public void setSets(List<UpdateExerciseTimeSetContentDto> sets)
    {
        super.setSets(sets);
    }
}
