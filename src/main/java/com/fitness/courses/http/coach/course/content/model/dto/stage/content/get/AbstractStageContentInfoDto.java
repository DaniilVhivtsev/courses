package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get;

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
        @JsonSubTypes.Type(value = ExercisesStageContentInfoDto.class, name = "ExercisesStageContentInfoDtoAuthor"),
        @JsonSubTypes.Type(value = ImgStageContentInfoDto.class, name = "ImgStageContentInfoDtoAuthor"),
        @JsonSubTypes.Type(value = TextStageContentInfoDto.class, name = "TextStageContentInfoDtoAuthor"),
        @JsonSubTypes.Type(value = VideoStageContentInfoDto.class, name = "VideoStageContentInfoDtoAuthor")
})
@Schema(
        description = "Parent description",
        discriminatorProperty = "classType",
        discriminatorMapping = {
                @DiscriminatorMapping(value = "ExercisesStageContentInfoDtoAuthor", schema = ExercisesStageContentInfoDto.class),
                @DiscriminatorMapping(value = "ImgStageContentInfoDtoAuthor", schema = ImgStageContentInfoDto.class),
                @DiscriminatorMapping(value = "TextStageContentInfoDtoAuthor", schema = TextStageContentInfoDto.class),
                @DiscriminatorMapping(value = "VideoStageContentInfoDtoAuthor", schema = VideoStageContentInfoDto.class)
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
