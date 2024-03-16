package com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "exerciseSetContentType"
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
        @JsonSubTypes.Type(
                name = "stage.distance.exercise.set.content",
                value = ExerciseDistanceSetContent.class
        ),
})
public abstract class AbstractExerciseSetContent
{
    private String uuid;

    private String countOfKilograms;

    private LocalTime pauseAfter;

    private String exerciseSetContentType;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getCountOfKilograms()
    {
        return countOfKilograms;
    }

    public void setCountOfKilograms(String countOfKilograms)
    {
        this.countOfKilograms = countOfKilograms;
    }

    public LocalTime getPauseAfter()
    {
        return pauseAfter;
    }

    public void setPauseAfter(LocalTime pauseAfter)
    {
        this.pauseAfter = pauseAfter;
    }

    public void setExerciseSetContentType(String exerciseSetContentType)
    {
        this.exerciseSetContentType = exerciseSetContentType;
    }

    @JsonTypeInfo(use = Id.NAME)
    public abstract String getExerciseSetContentType();

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
