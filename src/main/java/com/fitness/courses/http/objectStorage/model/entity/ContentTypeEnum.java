package com.fitness.courses.http.objectStorage.model.entity;

public enum ContentTypeEnum
{
    IMG("IMG"),
    VIDEO("VIDEO");

    private final String value;

    ContentTypeEnum(String value)
    {
        this.value = value;
    }
}
