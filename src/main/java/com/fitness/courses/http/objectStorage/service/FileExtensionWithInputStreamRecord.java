package com.fitness.courses.http.objectStorage.service;

import java.io.InputStream;

import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;

public record FileExtensionWithInputStreamRecord(FileExtensionEnum fileExtensionEnum, InputStream inputStream)
{
}
