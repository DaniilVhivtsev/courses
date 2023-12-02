package com.fitness.courses.http.objectStorage.service;

import java.io.InputStream;
import java.util.List;

import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;
import com.fitness.courses.http.objectStorage.model.entity.LocalStorageFileEntity;

public interface LocalStorageFileService
{
    LocalStorageFileEntity addFile(String bucketName, FileExtensionEnum fileExtension, InputStream inputStream);

    List<LocalStorageFileEntity> addFiles(String bucketName, List<FileExtensionWithInputStreamRecord> files);

    FileWithContentRecord getFile(String bucketName, String fileKey);

    LocalStorageFileEntity getFileEntity(String bucketName, String fileKey);
}
