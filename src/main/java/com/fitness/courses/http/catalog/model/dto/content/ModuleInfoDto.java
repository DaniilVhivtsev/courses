package com.fitness.courses.http.catalog.model.dto.content;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ сервера с публичной информацией о модуле в курсе.")
public class ModuleInfoDto
{
    @Schema(description = "Идентификатор модуля.")
    private Long id;

    @Schema(description = "Название модуля.")
    private String title;

    @Schema(description = "Порядковый номер модуля.")
    private int serialNumber;

    @Schema(description = "Урок модуля курса. (Содержание курса).")
    private List<LessonInfoDto> lessons;

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

    public int getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public List<LessonInfoDto> getLessons()
    {
        return lessons;
    }

    public void setLessons(List<LessonInfoDto> lessons)
    {
        this.lessons = lessons;
    }
}
