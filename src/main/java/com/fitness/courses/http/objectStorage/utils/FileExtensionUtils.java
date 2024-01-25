package com.fitness.courses.http.objectStorage.utils;

import com.fitness.courses.http.objectStorage.model.entity.ContentTypeEnum;
import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;

public class FileExtensionUtils
{
    private FileExtensionUtils()
    {
    }

    public static ContentTypeEnum getContentType(FileExtensionEnum fileExtension)
    {
        return switch (fileExtension)
                {
                    case JPG, PNG -> ContentTypeEnum.IMG;
                    case MOV, AVI, MP4 -> ContentTypeEnum.VIDEO;
                };
    }
}
