package com.fitness.courses.http.coach.course.content.model.entity.stage.content;

public class VideoStageContent extends AbstractStageContent
{
    private static final String DISCRIMINATOR = "stage.content.video";

    private Long attachmentId;

    public Long getAttachmentId()
    {
        return attachmentId;
    }

    public VideoStageContent setAttachmentId(Long attachmentId)
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
