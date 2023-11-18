package com.fitness.courses.http.user.dto;

import com.fitness.courses.global.Languages;

public class GeneralInfo
{
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private Languages currentLanguage;
    private String biography;
    private String about;
    private AttachmentDto iconImgDto;
    private String email;
    private String vkontakteLink;
    private String gender;
    private Float weight;
    private Float height;
    private Integer age;
    private boolean authByVkontakte;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getPatronymic()
    {
        return patronymic;
    }

    public void setPatronymic(String patronymic)
    {
        this.patronymic = patronymic;
    }

    public Languages getCurrentLanguage()
    {
        return currentLanguage;
    }

    public void setCurrentLanguage(Languages currentLanguage)
    {
        this.currentLanguage = currentLanguage;
    }

    public String getBiography()
    {
        return biography;
    }

    public void setBiography(String biography)
    {
        this.biography = biography;
    }

    public String getAbout()
    {
        return about;
    }

    public void setAbout(String about)
    {
        this.about = about;
    }

    public AttachmentDto getIconImgDto()
    {
        return iconImgDto;
    }

    public void setIconImgDto(AttachmentDto iconImgDto)
    {
        this.iconImgDto = iconImgDto;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getVkontakteLink()
    {
        return vkontakteLink;
    }

    public void setVkontakteLink(String vkontakteLink)
    {
        this.vkontakteLink = vkontakteLink;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public Float getWeight()
    {
        return weight;
    }

    public void setWeight(Float weight)
    {
        this.weight = weight;
    }

    public Float getHeight()
    {
        return height;
    }

    public void setHeight(Float height)
    {
        this.height = height;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public boolean isAuthByVkontakte()
    {
        return authByVkontakte;
    }

    public void setAuthByVkontakte(boolean authByVkontakte)
    {
        this.authByVkontakte = authByVkontakte;
    }
}
