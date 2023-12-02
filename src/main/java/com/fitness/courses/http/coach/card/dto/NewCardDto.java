package com.fitness.courses.http.coach.card.dto;

public class NewCardDto
{
    private String title;
    private String description;
    private String muscleGroupsDescription;
    private String inventoryDescription;

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
}
