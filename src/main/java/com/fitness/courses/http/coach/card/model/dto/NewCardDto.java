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

    public NewCardDto setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getDescription()
    {
        return description;
    }

    public NewCardDto setDescription(String description)
    {
        this.description = description;
        return this;
    }

    public String getMuscleGroupsDescription()
    {
        return muscleGroupsDescription;
    }

    public NewCardDto setMuscleGroupsDescription(String muscleGroupsDescription)
    {
        this.muscleGroupsDescription = muscleGroupsDescription;
        return this;
    }

    public String getInventoryDescription()
    {
        return inventoryDescription;
    }

    public NewCardDto setInventoryDescription(String inventoryDescription)
    {
        this.inventoryDescription = inventoryDescription;
        return this;
    }

    public List<MultipartFile> getImages()
    {
        return images;
    }

    public NewCardDto setImages(List<MultipartFile> images)
    {
        this.images = images;
        return this;
    }

    public MultipartFile getVideo()
    {
        return video;
    }

    public NewCardDto setVideo(MultipartFile video)
    {
        this.video = video;
        return this;
    }
}
