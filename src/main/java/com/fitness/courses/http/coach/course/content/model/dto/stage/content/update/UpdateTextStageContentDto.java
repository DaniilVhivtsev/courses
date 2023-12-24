package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update;

public class UpdateTextStageContentDto extends UpdateAbstractStageContentDto
{
    private String textContent;

    public String getTextContent()
    {
        return textContent;
    }

    public void setTextContent(String textContent)
    {
        this.textContent = textContent;
    }
}
