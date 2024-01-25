package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "TextStageContentInfoDtoAuthor")
public class TextStageContentInfoDto extends AbstractStageContentInfoDto
{
    private String textContent;

    public TextStageContentInfoDto()
    {
        super.type = StageContentType.TEXT;
    }

    public TextStageContentInfoDto(String textContent)
    {
        this.textContent = textContent;
        super.type = StageContentType.TEXT;
    }

    public String getTextContent()
    {
        return textContent;
    }

    public void setTextContent(String textContent)
    {
        this.textContent = textContent;
    }
}