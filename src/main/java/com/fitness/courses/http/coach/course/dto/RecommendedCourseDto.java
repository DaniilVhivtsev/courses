package com.fitness.courses.http.coach.course.dto;

import com.fitness.courses.http.user.dto.AttachmentDto;

public class RecommendedCourseDto
{
    private Long id;
    private String title;
    private String description;
    private AttachmentDto iconImgDto;
    private Integer rating;
    private Long numberOfPeople;
    private boolean isFree;
    private Integer price;

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

    public AttachmentDto getIconImgDto()
    {
        return iconImgDto;
    }

    public void setIconImgDto(AttachmentDto iconImgDto)
    {
        this.iconImgDto = iconImgDto;
    }

    public Integer getRating()
    {
        return rating;
    }

    public void setRating(Integer rating)
    {
        this.rating = rating;
    }

    public Long getNumberOfPeople()
    {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Long numberOfPeople)
    {
        this.numberOfPeople = numberOfPeople;
    }

    public boolean isFree()
    {
        return isFree;
    }

    public void setFree(boolean free)
    {
        isFree = free;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }
}
