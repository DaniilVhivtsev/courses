package com.fitness.courses.http.user.dto;

public class UserCurrentCourseInfo
{
    private Long id;

    private String title;

    private String description;

    private String iconImgUrl;

    private Long authorId;

    private String authorFullName;

    private Double percentagePassed;

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

    public Double getPercentagePassed()
    {
        return percentagePassed;
    }

    public void setPercentagePassed(Double percentagePassed)
    {
        this.percentagePassed = percentagePassed;
    }
}
