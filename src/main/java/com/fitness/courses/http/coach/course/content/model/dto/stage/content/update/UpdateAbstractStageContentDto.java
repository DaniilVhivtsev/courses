package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UpdateExercisesStageContentDto.class, name = "UpdateExercisesStageContentDto"),
        @JsonSubTypes.Type(value = UpdateImgStageContentDto.class, name = "UpdateImgStageContentDto"),
        @JsonSubTypes.Type(value = UpdateTextStageContentDto.class, name = "UpdateTextStageContentDto"),
        @JsonSubTypes.Type(value = UpdateVideoStageContentDto.class, name = "UpdateVideoStageContentDto")
})
public abstract class UpdateAbstractStageContentDto
{
    private String uuid;

    private Integer serialNumber;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public Integer getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber)
    {
        this.serialNumber = serialNumber;
    }
}
