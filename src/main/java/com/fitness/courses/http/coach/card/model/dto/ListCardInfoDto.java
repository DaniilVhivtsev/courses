package com.fitness.courses.http.coach.card.model.dto;

import com.fitness.courses.http.attachment.model.dto.AttachmentInfoDto;

public class ListCardInfoDto
{
    private Long id;
    private String title;
    private AttachmentInfoDto mainImage;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public AttachmentInfoDto getMainImage()
    {
        return mainImage;
    }

    public void setMainImage(AttachmentInfoDto mainImage)
    {
        this.mainImage = mainImage;
    }
}
