package com.fitness.courses.http.coach.course.content.model.entity.stage;

public class ImgStageContent extends AbstractStageContent
{
    private static final String DISCRIMINATOR = "stage.content.img";

    private Long attachmentId;

    public Long getAttachmentId()
    {
        return attachmentId;
    }

    public ImgStageContent setAttachmentId(Long attachmentId)
    {
        this.attachmentId = attachmentId;
        return this;
    }

    @Override
    public String getType()
    {
        return DISCRIMINATOR;
    }
}
