package com.fitness.courses.http.catalog.model.dto.content;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ сервера с публичной информацией об этапе в уроке курса.")
public class StageInfoDto
{
    @Schema(description = "Идентификатор этапа.")
    private Long id;

    @Schema(description = "Название этапа.")
    private String title;

    @Schema(description = "Порядковый номер этапа.")
    private int serialNumber;

    @Schema(description = "Выполнен ли этап или нет. Если пользователь не прошел аутентификацию или не является "
            + "студентом курса, то значение будет false")
    private boolean isCompleted;

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

    public boolean getIsCompleted()
    {
        return isCompleted;
    }

    public void setCompleted(boolean completed)
    {
        isCompleted = completed;
    }
}
