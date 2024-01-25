package com.fitness.courses.http.user.model.dto;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Информация для изменения пользователя. Значения передаются через form-data. Также необходимо в хедере "
        + "указать Content-Type равным multipart form-data")
public class EditUserGeneralInfoDto
{
    @Schema(description = "Имя пользователя.")
    private String name;

    @Schema(description = "Фамилия пользователя.")
    private String surname;

    @Schema(description = "Краткая биография пользователя.")
    private String biography;

    @Schema(description = "О пользователе информация.")
    private String about;

    @Schema(description = "Файл для загрузки", type = "string", format = "binary")
    private MultipartFile icon;

    public String getName()
    {
        return name;
    }

    public EditUserGeneralInfoDto setName(String name)
    {
        this.name = name;
        return this;
    }

    public String getSurname()
    {
        return surname;
    }

    public EditUserGeneralInfoDto setSurname(String surname)
    {
        this.surname = surname;
        return this;
    }

    public String getBiography()
    {
        return biography;
    }

    public EditUserGeneralInfoDto setBiography(String biography)
    {
        this.biography = biography;
        return this;
    }

    public String getAbout()
    {
        return about;
    }

    public EditUserGeneralInfoDto setAbout(String about)
    {
        this.about = about;
        return this;
    }

    public MultipartFile getIcon()
    {
        return icon;
    }

    public EditUserGeneralInfoDto setIcon(MultipartFile icon)
    {
        this.icon = icon;
        return this;
    }
}
