package com.fitness.courses.http.student.model.dto.achievement;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ сервера с достижениями в курсе пользователя.")
public class UserCourseWithAchievementsInfoDto
{
    @Schema(description = "Идентификатор курса.")
    private Long id;

    @Schema(description = "Название курса.")
    private String title;

    @Schema(description = "Картинка курса.")
    private String imgUrl;

    @Schema(description = "Достижения пользователя по курсу.")
    private List<UserCourseAchievementInfoDto> userAchievements;

    public Long getId()
    {
        return id;
    }

    public UserCourseWithAchievementsInfoDto setId(Long id)
    {
        this.id = id;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public UserCourseWithAchievementsInfoDto setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public UserCourseWithAchievementsInfoDto setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
        return this;
    }

    public List<UserCourseAchievementInfoDto> getUserAchievements()
    {
        return userAchievements;
    }

    public UserCourseWithAchievementsInfoDto setUserAchievements(
            List<UserCourseAchievementInfoDto> userAchievements)
    {
        this.userAchievements = userAchievements;
        return this;
    }
}
