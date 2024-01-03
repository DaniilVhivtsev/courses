package com.fitness.courses.http.catalog.model.dto;

public class CatalogPopularCourseInfoDto
{
    private Long id;
    private String title;
    private String description;
    private String iconImgUrl;
    private Double rating;
    private Integer numberOfPeople;
    private boolean isFree;
    private Integer price;
    private Long authorId;
    private String authorFullName;
    private boolean userIsRegisteredForTheCourse;

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

    public String getIconImgUrl()
    {
        return iconImgUrl;
    }

    public void setIconImgUrl(String iconImgUrl)
    {
        this.iconImgUrl = iconImgUrl;
    }

    public Double getRating()
    {
        return rating;
    }

    public void setRating(Double rating)
    {
        this.rating = rating;
    }

    public Integer getNumberOfPeople()
    {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople)
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

    public Long getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(Long authorId)
    {
        this.authorId = authorId;
    }

    public String getAuthorFullName()
    {
        return authorFullName;
    }

    public void setAuthorFullName(String authorFullName)
    {
        this.authorFullName = authorFullName;
    }

    public boolean isUserIsRegisteredForTheCourse()
    {
        return userIsRegisteredForTheCourse;
    }

    public void setUserIsRegisteredForTheCourse(boolean userIsRegisteredForTheCourse)
    {
        this.userIsRegisteredForTheCourse = userIsRegisteredForTheCourse;
    }
}
