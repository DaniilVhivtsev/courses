package com.fitness.courses.http.coach.course.content.model.dto.stage;

import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.StageContentType;

public class AddCourseAuthorStageContentInfoDto
{
    private StageContentType type;

    public StageContentType getType()
    {
        return type;
    }

    public void setType(StageContentType type)
    {
        this.type = type;
    }
}
