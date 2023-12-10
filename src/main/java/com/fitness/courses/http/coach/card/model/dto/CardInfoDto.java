package com.fitness.courses.http.coach.card.model.dto;

import java.util.List;

import com.fitness.courses.http.attachment.model.dto.AttachmentInfoDto;

public class CardInfoDto
{
    private Long id;
    private String title;
    private String description;
    private String muscleGroupsDescription;
    private String inventoryDescription;
    private List<AttachmentInfoDto> images;
    private AttachmentInfoDto video;

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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getMuscleGroupsDescription()
    {
        return muscleGroupsDescription;
    }

    public void setMuscleGroupsDescription(String muscleGroupsDescription)
    {
        this.muscleGroupsDescription = muscleGroupsDescription;
    }

    public String getInventoryDescription()
    {
        return inventoryDescription;
    }

    public void setInventoryDescription(String inventoryDescription)
    {
        this.inventoryDescription = inventoryDescription;
    }

    public List<AttachmentInfoDto> getImages()
    {
        return images;
    }

    public void setImages(List<AttachmentInfoDto> images)
    {
        this.images = images;
    }

    public AttachmentInfoDto getVideo()
    {
        return video;
    }

    public void setVideo(AttachmentInfoDto video)
    {
        this.video = video;
    }
}
