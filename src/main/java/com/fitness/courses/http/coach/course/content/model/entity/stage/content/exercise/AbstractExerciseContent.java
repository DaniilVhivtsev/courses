package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.AbstractExerciseSetContent;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                name = "stage.repeat.exercise.content",
                value = RepeatExerciseContent.class
        ),
        @JsonSubTypes.Type(
                name = "stage.time.exercise.content",
                value = TimeExerciseContent.class
        ),
})
public abstract class AbstractExerciseContent<T extends AbstractExerciseSetContent>
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

    public abstract List<T> getSets();

    public abstract void setSets(List<T> sets);

    @JsonTypeInfo(use = Id.NAME)
    public abstract String getType();

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        AbstractExerciseContent<?> that = (AbstractExerciseContent<?>)o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(cardId, that.cardId)
                && Objects.equals(sets, that.sets);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uuid, cardId, sets);
    }
}
