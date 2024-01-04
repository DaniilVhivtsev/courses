package com.fitness.courses.http.student.model.dto.stage;

import java.util.ArrayList;
import java.util.List;

import com.fitness.courses.http.student.model.dto.stage.content.AbstractStageContentInfoDto;

public class StageContentInfoDto
{
    private Long id;

    private boolean isCompleted;

    private List<AbstractStageContentInfoDto> stageContent = new ArrayList<>();

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public boolean isCompleted()
    {
        return isCompleted;
    }

    public void setCompleted(boolean completed)
    {
        isCompleted = completed;
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
