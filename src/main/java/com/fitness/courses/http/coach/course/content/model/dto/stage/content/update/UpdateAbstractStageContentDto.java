package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.StageContentType;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UpdateExercisesStageContentDto.class, name = "EXERCISES"),
        @JsonSubTypes.Type(value = UpdateImgStageContentDto.class, name = "IMG"),
        @JsonSubTypes.Type(value = UpdateTextStageContentDto.class, name = "TEXT"),
        @JsonSubTypes.Type(value = UpdateVideoStageContentDto.class, name = "VIDEO")
})
public abstract class UpdateAbstractStageContentDto
{
    private String uuid;

    private Integer serialNumber;

    private StageContentType type;

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

    public StageContentType getType()
    {
        return type;
    }

    public void setType(StageContentType type)
    {
        this.type = type;
    }
}
