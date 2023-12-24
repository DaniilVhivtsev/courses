package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateAbstractExerciseSetContentDto;

public abstract class UpdateAbstractExerciseContentDto<T extends UpdateAbstractExerciseSetContentDto>
{
    private String uuid;

    private Long cardId;

    protected List<T> sets;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public Long getCardId()
    {
        return cardId;
    }

    public void setCardId(Long cardId)
    {
        this.cardId = cardId;
    }

    public List<T> getSets()
    {
        return sets;
    }

    public void setSets(List<T> sets)
    {
        this.sets = sets;
    }
}
