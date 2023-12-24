package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get;

public enum StageContentType
{
    IMG("IMG"),
    VIDEO("VIDEO"),
    TEXT("TEXT"),
    EXERCISES("EXERCISES");

    private final String value;

    StageContentType(String value)
    {
        this.value = value;
    }
}
