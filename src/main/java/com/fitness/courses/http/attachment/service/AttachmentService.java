package com.fitness.courses.http.attachment.service;

import java.net.MalformedURLException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;

public interface AttachmentService
{
    UrlResource getFile(String bucketName, String fileKey) throws MalformedURLException;
}
