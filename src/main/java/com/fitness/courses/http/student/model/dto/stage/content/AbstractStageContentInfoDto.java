package com.fitness.courses.http.student.model.dto.stage.content;

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
