package com.fitness.courses.http.attachment.service;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.objectStorage.model.entity.LocalStorageFileEntity;
import com.fitness.courses.http.objectStorage.service.LocalStorageFileService;

@Service
public class AttachmentServiceImpl implements AttachmentService
{
    private final LocalStorageFileService localStorageFileService;

    @Autowired
    public AttachmentServiceImpl(
            LocalStorageFileService localStorageFileService)
    {
        this.localStorageFileService = localStorageFileService;
    }

    @Override
    public UrlResource getFile(String bucketName, String fileKey) throws MalformedURLException
    {
        LocalStorageFileEntity localStorageFileEntity = localStorageFileService.getFileEntity(bucketName, fileKey);
        return new UrlResource(localStorageFileEntity.getUrl());
    }
}
