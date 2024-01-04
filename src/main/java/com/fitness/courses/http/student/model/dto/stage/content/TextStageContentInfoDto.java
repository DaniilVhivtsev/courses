package com.fitness.courses.http.student.model.dto.stage.content;

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