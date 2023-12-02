package com.fitness.courses.http.objectStorage.model.entity;

public enum FileExtensionEnum
{
    PNG("png"),
    JPG("jpg");

    private final String value;

    FileExtensionEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
