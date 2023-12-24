package com.fitness.courses.http.attachment.service;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.UrlResource;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;
import com.fitness.courses.http.attachment.model.info.MultipartFileWithExtension;

public interface AttachmentService
{
    UrlResource getFile(String bucketName, String fileKey) throws MalformedURLException;

    AttachmentEntity add(MultipartFileWithExtension multipartFileWithExtension);

    AttachmentEntity findById(Long attachmentId);

    @Transactional
    void delete(Long attachmentId);

    List<AttachmentEntity> add(List<MultipartFileWithExtension> multipartFileWithExtensions);
}
