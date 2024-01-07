package com.fitness.courses.http.catalog.model.dto.content;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ сервера с публичной информацией об уроке модуля в курсе.")
public class LessonInfoDto
{
    @Schema(description = "Идентификатор урока.")
    private Long id;

    @Schema(description = "Название урока.")
    private String title;

    @Schema(description = "Порядковый номер урока.")
    private int serialNumber;

    @Schema(description = "Количество этапов в уроке.")
    private int stagesNumber;

    @Schema(description = "Количество пройденных этапов в уроке. Если пользователь не прошел аутентификацию или не "
            + "является студентом курса, то значение будет 0")
    private int completedStagesCount;

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

    public int getStagesNumber()
    {
        return stagesNumber;
    }

    public void setStagesNumber(int stagesNumber)
    {
        this.stagesNumber = stagesNumber;
    }

    public int getCompletedStagesCount()
    {
        return completedStagesCount;
    }

    public void setCompletedStagesCount(int completedStagesCount)
    {
        this.completedStagesCount = completedStagesCount;
    }
}
