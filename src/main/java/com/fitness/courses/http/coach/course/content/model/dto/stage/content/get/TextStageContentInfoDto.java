package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get;

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