package com.fitness.courses.http.student.model.dto.stage;

import java.util.ArrayList;
import java.util.List;

import com.fitness.courses.http.student.model.dto.stage.content.AbstractStageContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.ExercisesStageContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.ImgStageContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.TextStageContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.VideoStageContentInfoDto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public class StageContentInfoDto
{
    private Long id;

    private String title;

    private boolean isCompleted;

    @ArraySchema(
            schema = @Schema(
                    oneOf = {
                            ExercisesStageContentInfoDto.class,
                            ImgStageContentInfoDto.class,
                            TextStageContentInfoDto.class,
                            VideoStageContentInfoDto.class
                    }
            )
    )
    private List<AbstractStageContentInfoDto> stageContent = new ArrayList<>();

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean getIsCompleted()
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
