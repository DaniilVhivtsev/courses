package com.fitness.courses.http.coach.course.content.model.dto.stage.content;

public abstract class AbstractStageContentInfoDto
{
    private String uuid;

    protected StageContentType type;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
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
