package com.fitness.courses.http.coach.course.content.model.entity.stage.content;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "stageContentType"
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

    private int serialNumber;

    private String stageContentType;

    public String getUuid()
    {
        return uuid;
    }

    public AbstractStageContent setUuid(String uuid)
    {
        this.uuid = uuid;
        return this;
    }

    public int getSerialNumber()
    {
        return serialNumber;
    }

    public AbstractStageContent setSerialNumber(int serialNumber)
    {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setStageContentType(String stageContentType)
    {
        this.stageContentType = stageContentType;
    }

    @JsonTypeInfo(use = Id.NAME)
    public abstract String getStageContentType();

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
        return serialNumber == that.serialNumber && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(uuid, serialNumber);
    }
}
