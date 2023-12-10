package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                name = "stage.repeat.exercise.set.content",
                value = ExerciseRepeatSetContent.class
        ),
        @JsonSubTypes.Type(
                name = "stage.time.exercise.set.content",
                value = ExerciseTimeSetContent.class
        ),
})
public abstract class AbstractExerciseSetContent
{
    private String uuid;

    private Float countOfKilograms;

    private LocalDateTime pauseAfter;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public Float getCountOfKilograms()
    {
        return countOfKilograms;
    }

    public void setCountOfKilograms(Float countOfKilograms)
    {
        this.countOfKilograms = countOfKilograms;
    }

    public LocalDateTime getPauseAfter()
    {
        return pauseAfter;
    }

    public void setPauseAfter(LocalDateTime pauseAfter)
    {
        this.pauseAfter = pauseAfter;
    }

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

        AbstractExerciseSetContent that = (AbstractExerciseSetContent)o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(countOfKilograms,
                that.countOfKilograms) && Objects.equals(pauseAfter, that.pauseAfter);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uuid, countOfKilograms, pauseAfter);
    }
}
