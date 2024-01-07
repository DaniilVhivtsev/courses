package com.fitness.courses.http.coach.course.content.model.dto.stage;

import java.util.ArrayList;
import java.util.List;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.AbstractStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.ExercisesStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.ImgStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.TextStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.VideoStageContentInfoDto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public class CourseAuthorStageWithContentInfoDto
{
    private Long id;

    private int serialNumber;

    private String title;

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

    public int getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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
