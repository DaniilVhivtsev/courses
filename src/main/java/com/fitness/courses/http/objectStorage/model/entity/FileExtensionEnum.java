package com.fitness.courses.http.objectStorage.model.entity;

import com.fitness.courses.global.exceptions.ValidationException;

public enum FileExtensionEnum
{
    PNG("png"),
    JPG("jpg"),
    MOV("mov"),
    AVI("avi"),
    MP4("mp4");

    private final String value;

    FileExtensionEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public static FileExtensionEnum getEnum(String value) {
        for(FileExtensionEnum v : values())
        {
            if (v.getValue().equalsIgnoreCase(value))
            {
                return v;
            }
        }

        String message = "Extension \"%s\" not supported".formatted(value);
        throw new ValidationException(message);
    }
}
