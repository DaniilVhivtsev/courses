package com.fitness.courses.http.catalog.model.dto.content;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ сервера с публичной информацией об уроке модуля в курсе с этапами.")
public class LessonWithStagesInfoDto
{
    @Schema(description = "Идентификатор урока.")
    private Long id;

    @Schema(description = "Название урока.")
    private String title;

    @Schema(description = "Этапы урока'")
    private List<StageInfoDto> stages;

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

    public List<StageInfoDto> getStages()
    {
        return stages;
    }

    public void setStages(List<StageInfoDto> stages)
    {
        this.stages = stages;
    }
}
