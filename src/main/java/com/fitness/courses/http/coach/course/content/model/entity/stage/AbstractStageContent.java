package com.fitness.courses.http.coach.course.content.model.entity.stage;

import java.io.Serializable;
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
                name = "stage.content.exercises",
                value = ExercisesStageContent.class
        ),
        @JsonSubTypes.Type(
                name = "stage.content.img",
                value = ImgStageContent.class
        ),
        @JsonSubTypes.Type(
                name = "stage.content.text",
                value = TextStageContent.class
        ),
        @JsonSubTypes.Type(
                name = "stage.content.video",
                value = VideoStageContent.class
        ),
})
public abstract class AbstractStageContent implements Serializable
{
    private String uuid;

    public String getUuid()
    {
        return uuid;
    }

    public AbstractStageContent setUuid(String uuid)
    {
        this.uuid = uuid;
        return this;
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

        AbstractStageContent that = (AbstractStageContent)o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uuid);
    }
}
