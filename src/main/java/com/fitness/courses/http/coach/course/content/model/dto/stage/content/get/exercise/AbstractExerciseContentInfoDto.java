package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set.AbstractExerciseSetContentInfoDto;

import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type second",
        defaultImpl = AbstractExerciseContentInfoDto.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DistanceExerciseContentInfoDto.class, name = "DistanceExerciseContentInfoDtoAuthor"),
        @JsonSubTypes.Type(value = RepeatExerciseContentInfoDto.class, name = "RepeatExerciseContentInfoDtoAuthor"),
        @JsonSubTypes.Type(value = TimeExerciseContentInfoDto.class, name = "TimeExerciseContentInfoDtoAuthor")
})
@Schema(
        description = "Parent description",
        discriminatorProperty = "type second",
        discriminatorMapping = {
                @DiscriminatorMapping(value = "DistanceExerciseContentInfoDtoAuthor", schema = DistanceExerciseContentInfoDto.class),
                @DiscriminatorMapping(value = "RepeatExerciseContentInfoDtoAuthor", schema = RepeatExerciseContentInfoDto.class),
                @DiscriminatorMapping(value = "TimeExerciseContentInfoDtoAuthor", schema = TimeExerciseContentInfoDto.class)
        }
)
public abstract class AbstractExerciseContentInfoDto<T extends AbstractExerciseSetContentInfoDto>
{
    private String uuid;

    private Long cardId;

    protected List<T> sets;

    protected ExerciseContentType type;

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
}
