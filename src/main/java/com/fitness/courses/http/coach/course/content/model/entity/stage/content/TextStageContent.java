package com.fitness.courses.http.coach.course.content.model.entity.stage.content;

import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.TextContentString;

public class TextStageContent extends AbstractStageContent
{
    private static final String DISCRIMINATOR = "stage.content.text";

    private TextContentString textContentString;

    private String textContent;

    public TextContentString getTextContentString()
    {
        return textContentString;
    }

    public void setTextContentString(
            TextContentString textContentString)
    {
        this.textContentString = textContentString;
    }

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
