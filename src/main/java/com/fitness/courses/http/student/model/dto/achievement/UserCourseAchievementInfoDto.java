package com.fitness.courses.http.student.model.dto.achievement;

public class UserCourseAchievementInfoDto
{
    private Long id;

    private String title;

    private String description;

    private String imgUrl;

    public Long getId()
    {
        return id;
    }

    public UserCourseAchievementInfoDto setId(Long id)
    {
        this.id = id;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public UserCourseAchievementInfoDto setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getDescription()
    {
        return description;
    }

    public UserCourseAchievementInfoDto setDescription(String description)
    {
        this.description = description;
        return this;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public UserCourseAchievementInfoDto setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
        return this;
    }
}
