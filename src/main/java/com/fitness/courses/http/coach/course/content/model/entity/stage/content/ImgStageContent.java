package com.fitness.courses.http.coach.course.content.model.entity.stage.content;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.ImgId;

@JsonTypeName("stage.content.img")
public class ImgStageContent extends AbstractStageContent
{
    private static final String DISCRIMINATOR = "stage.content.img";

    private ImgId imgId;

    private Long imgAttachmentId;

    public ImgId getImgId()
    {
        return imgId;
    }

    public void setImgId(ImgId imgId)
    {
        this.imgId = imgId;
    }

    public Long getImgAttachmentId()
    {
        return imgAttachmentId;
    }

    public void setImgAttachmentId(Long imgAttachmentId)
    {
        this.imgAttachmentId = imgAttachmentId;
    }

    @Override
    public String getStageContentType()
    {
        return DISCRIMINATOR;
    }
}
