package com.fitness.courses.http.student.model.dto.stage.content.exercise;

import java.util.List;

import com.fitness.courses.http.student.model.dto.stage.content.exercise.set.AbstractExerciseSetContentInfoDto;

public abstract class AbstractExerciseContentInfoDto<T extends AbstractExerciseSetContentInfoDto>
{
    private String uuid;

    private Long cardId;

    protected List<T> sets;

    protected ExerciseContentType type;

    private long numberOfSets;

    private long numberOfCompletedSets;

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

    public ExerciseContentType getType()
    {
        return type;
    }

    protected void setType(ExerciseContentType type)
    {
        this.type = type;
    }

    public long getNumberOfSets()
    {
        return numberOfSets;
    }

    public void setNumberOfSets(long numberOfSets)
    {
        this.numberOfSets = numberOfSets;
    }

    public long getNumberOfCompletedSets()
    {
        return numberOfCompletedSets;
    }

    public void setNumberOfCompletedSets(long numberOfCompletedSets)
    {
        this.numberOfCompletedSets = numberOfCompletedSets;
    }
}
