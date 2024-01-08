package com.fitness.courses.http.coach.course.content.model.entity.stage.content;

import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.VideoId;

public class VideoStageContent extends AbstractStageContent
{
    private static final String DISCRIMINATOR = "stage.content.video";

    private VideoId videoId;

    private Long videoAttachmentId;

    public VideoId getVideoId()
    {
        return videoId;
    }

    public void setVideoId(VideoId videoId)
    {
        this.videoId = videoId;
    }

    public Long getVideoAttachmentId()
    {
        return videoAttachmentId;
    }

    public void setVideoAttachmentId(Long videoAttachmentId)
    {
        this.videoAttachmentId = videoAttachmentId;
    }

    @Override
    public String getType()
    {
        return DISCRIMINATOR;
    }
}
