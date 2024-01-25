package com.fitness.courses.http.catalog.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ сервера с информацией о найденном курсе по ключевому слову в каталоге.")
public class CatalogBySearchValueCourseInfoDto
{
    @Schema(description = "Идентификатор курса.")
    private Long id;

    @Schema(description = "Название курса.")
    private String title;

    @Schema(description = "Описание курса.")
    private String description;

    @Schema(description = "Url для получения иконки курса.")
    private String iconImgUrl;

    @Schema(description = "Рейтинг курса.")
    private Double rating;

    @Schema(description = "Количество студентов на курсе.")
    private Integer numberOfPeople;

    @Schema(description = "Бесплатный ли курс. true - курс бесплатный, false - курс платный).")
    private boolean isFree;

    @Schema(description = "Цена за курс. (Если курс платный)")
    private Integer price;

    @Schema(description = "Идентификатор автора курса.")
    private Long authorId;

    @Schema(description = "Полное имя (Фамилия Имя) автора курса.")
    private String authorFullName;

    @Schema(description = "Пользователь является студентом данного курса. true - если пользователь прошел "
            + "аутентификацию и является студентом, иначе - false.")
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

    public boolean getIsFree()
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
