package com.fitness.courses.http.coach.course.content.model.entity.stage.content;

public class TextStageContent extends AbstractStageContent
{
    private static final String DISCRIMINATOR = "stage.content.text";

    private String textContent;

    public String getTextContent()
    {
        return textContent;
    }

    public TextStageContent setTextContent(String textContent)
    {
        this.textContent = textContent;
        return this;
    }

    @Override
    public String getType()
    {
        return DISCRIMINATOR;
    }
}
