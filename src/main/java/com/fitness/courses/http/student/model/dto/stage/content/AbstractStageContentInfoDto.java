package com.fitness.courses.http.student.model.dto.stage.content;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "classType",
        defaultImpl = AbstractStageContentInfoDto.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ExercisesStageContentInfoDto.class, name = "ExercisesStageContentInfoDto"),
        @JsonSubTypes.Type(value = ImgStageContentInfoDto.class, name = "ImgStageContentInfoDto"),
        @JsonSubTypes.Type(value = TextStageContentInfoDto.class, name = "TextStageContentInfoDto"),
        @JsonSubTypes.Type(value = VideoStageContentInfoDto.class, name = "VideoStageContentInfoDto")
})
@Schema(
        description = "Parent description",
        discriminatorProperty = "classType",
        discriminatorMapping = {
                @DiscriminatorMapping(value = "ExercisesStageContentInfoDto", schema = ExercisesStageContentInfoDto.class),
                @DiscriminatorMapping(value = "ImgStageContentInfoDto", schema = ImgStageContentInfoDto.class),
                @DiscriminatorMapping(value = "TextStageContentInfoDto", schema = TextStageContentInfoDto.class),
                @DiscriminatorMapping(value = "VideoStageContentInfoDto", schema = VideoStageContentInfoDto.class)
        }
)
public abstract class AbstractStageContentInfoDto
{
    private String uuid;

    private int serialNumber;

    protected StageContentType type;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public int getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public StageContentType getType()
    {
        return type;
    }

    protected void setType(StageContentType type)
    {
        this.type = type;
    }
}
