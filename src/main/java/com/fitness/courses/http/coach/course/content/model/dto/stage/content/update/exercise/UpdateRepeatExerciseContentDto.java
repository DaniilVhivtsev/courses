package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseRepeatSetContentDto;

public class UpdateRepeatExerciseContentDto extends UpdateAbstractExerciseContentDto<UpdateExerciseRepeatSetContentDto>
{
    @Override
    public List<UpdateExerciseRepeatSetContentDto> getSets()
    {
        return super.getSets();
    }

    @Override
    public void setSets(List<UpdateExerciseRepeatSetContentDto> sets)
    {
        super.setSets(sets);
    }
}
