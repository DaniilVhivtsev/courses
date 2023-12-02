package com.fitness.courses.http.coach.card.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class NewCardDto
{
    private String title;
    private String description;
    private String muscleGroupsDescription;
    private String inventoryDescription;
    private List<MultipartFile> images = new ArrayList<>();
    private MultipartFile video;

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

    public List<MultipartFile> getImages()
    {
        return images;
    }

    public void setImages(List<MultipartFile> images)
    {
        this.images = images;
    }

    public MultipartFile getVideo()
    {
        return video;
    }

    public void setVideo(MultipartFile video)
    {
        this.video = video;
    }
}
