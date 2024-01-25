package com.fitness.courses.http.objectStorage.service;

import java.io.InputStream;

import com.fitness.courses.http.objectStorage.model.entity.LocalStorageFileEntity;

public record FileWithContentRecord(LocalStorageFileEntity file, InputStream inputStream)
{
}
