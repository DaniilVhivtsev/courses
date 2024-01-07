package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateAbstractExerciseSetContentDto;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UpdateDistanceExerciseContentDto.class, name = "UpdateDistanceExerciseContentDto"),
        @JsonSubTypes.Type(value = UpdateRepeatExerciseContentDto.class, name = "UpdateRepeatExerciseContentDto"),
        @JsonSubTypes.Type(value = UpdateTimeExerciseContentDto.class, name = "UpdateTimeExerciseContentDto")
})
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
