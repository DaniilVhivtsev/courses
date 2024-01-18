package com.fitness.courses.http.student.model.dto.stage.content.exercise;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.set.AbstractExerciseSetContentInfoDto;

import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type second",
        defaultImpl = AbstractExerciseContentInfoDto.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DistanceExerciseContentInfoDto.class, name = "DistanceExerciseContentInfoDto"),
        @JsonSubTypes.Type(value = RepeatExerciseContentInfoDto.class, name = "RepeatExerciseContentInfoDto"),
        @JsonSubTypes.Type(value = TimeExerciseContentInfoDto.class, name = "TimeExerciseContentInfoDto")
})
@Schema(
        description = "Parent description",
        discriminatorProperty = "type second",
        discriminatorMapping = {
                @DiscriminatorMapping(value = "DistanceExerciseContentInfoDto", schema = DistanceExerciseContentInfoDto.class),
                @DiscriminatorMapping(value = "RepeatExerciseContentInfoDto", schema = RepeatExerciseContentInfoDto.class),
                @DiscriminatorMapping(value = "TimeExerciseContentInfoDto", schema = TimeExerciseContentInfoDto.class)
        }
)
public abstract class AbstractExerciseContentInfoDto<T extends AbstractExerciseSetContentInfoDto>
{
    private String uuid;

    private Long cardId;

    private String cardTitle;

    private String cardImgUrl;

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

    public String getCardTitle()
    {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle)
    {
        this.cardTitle = cardTitle;
    }

    public String getCardImgUrl()
    {
        return cardImgUrl;
    }

    public void setCardImgUrl(String cardImgUrl)
    {
        this.cardImgUrl = cardImgUrl;
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
