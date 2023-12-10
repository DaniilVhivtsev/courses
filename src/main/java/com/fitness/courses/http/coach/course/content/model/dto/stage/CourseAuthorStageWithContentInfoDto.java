package com.fitness.courses.http.coach.course.content.model.dto.stage;

import java.util.ArrayList;
import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.AbstractStageContentInfoDto;

public class CourseAuthorStageWithContentInfoDto
{
    private Long id;

    private int serialNumber;

    private List<AbstractStageContentInfoDto> stageContent = new ArrayList<>();

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public List<AbstractStageContentInfoDto> getStageContent()
    {
        return stageContent;
    }

    public void setStageContent(
            List<AbstractStageContentInfoDto> stageContent)
    {
        this.stageContent = stageContent;
    }
}
