package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseDistanceSetContentDto;

public class UpdateDistanceExerciseContentDto extends UpdateAbstractExerciseContentDto<UpdateExerciseDistanceSetContentDto>
{
    @Override
    public List<UpdateExerciseDistanceSetContentDto> getSets()
    {
        return super.getSets();
    }

    @Override
    public void setSets(List<UpdateExerciseDistanceSetContentDto> sets)
    {
        super.setSets(sets);
    }
}
