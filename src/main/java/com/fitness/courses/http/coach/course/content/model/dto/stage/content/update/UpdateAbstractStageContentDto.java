package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update;

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
