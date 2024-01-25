package com.fitness.courses.http.attachment.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.configuration.s3.YandexS3Config;
import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;
import com.fitness.courses.http.attachment.model.info.MultipartFileWithExtension;
import com.fitness.courses.http.objectStorage.model.entity.LocalStorageFileEntity;
import com.fitness.courses.http.objectStorage.service.LocalStorageFileService;

@Service
public class AttachmentServiceImpl implements AttachmentService
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    private final CrudAttachmentService crudAttachmentService;
    private final LocalStorageFileService localStorageFileService;
    private final YandexS3Config yandexS3Config;

    @Autowired
    public AttachmentServiceImpl(CrudAttachmentService crudAttachmentService,
            LocalStorageFileService localStorageFileService,
            YandexS3Config yandexS3Config)
    {
        this.crudAttachmentService = crudAttachmentService;
        this.localStorageFileService = localStorageFileService;
        this.yandexS3Config = yandexS3Config;
    }

    @Override
    public UrlResource getFile(String bucketName, String fileKey) throws MalformedURLException
    {
        LocalStorageFileEntity localStorageFileEntity = localStorageFileService.getFileEntity(bucketName, fileKey);
        return new UrlResource(localStorageFileEntity.getUrl());
    }

    @Override
    @Transactional
    public AttachmentEntity add(MultipartFileWithExtension multipartFileWithExtension)
    {
        AttachmentEntity entity = new AttachmentEntity();
        entity.setFileName(getFileName(multipartFileWithExtension.file()));

        InputStream fileInputStream;
        try
        {
            fileInputStream = multipartFileWithExtension.file().getInputStream();
        }
        catch (IOException e)
        {
            final String message = "Error get file inputStream";
            LOG.error(message);
            throw new RuntimeException();
        }
        entity.setFileEntity(
                localStorageFileService.addFile(yandexS3Config.getMainBucketName(),
                        multipartFileWithExtension.fileExtension(), fileInputStream)
        );

        return crudAttachmentService.save(entity);
    }

    @Override
    public AttachmentEntity findById(Long attachmentId)
    {
        return crudAttachmentService.getById(attachmentId).orElseThrow();
    }

    @Override
    @Transactional
    public void delete(Long attachmentId)
    {
        AttachmentEntity attachmentEntity = crudAttachmentService.getById(attachmentId).orElseThrow();
        localStorageFileService.delete(attachmentEntity.getFileEntity().getId());
        crudAttachmentService.delete(attachmentId);
    }

    @Override
    @Transactional
    public List<AttachmentEntity> add(List<MultipartFileWithExtension> multipartFileWithExtensions)
    {
        List<TupleFileNameWithLocalStorageFile> tupleLocalStorageFiles = multipartFileWithExtensions.stream()
                .map(multipartFileWithExtension ->
                {
                    InputStream fileInputStream;
                    try
                    {
                        fileInputStream = multipartFileWithExtension.file().getInputStream();
                    }
                    catch (IOException e)
                    {
                        final String message = "Error get file inputStream";
                        LOG.error(message);
                        throw new RuntimeException();
                    }

                    return new TupleFileNameWithLocalStorageFile(
                            getFileName(multipartFileWithExtension.file()),
                            localStorageFileService.addFile(yandexS3Config.getMainBucketName(),
                                    multipartFileWithExtension.fileExtension(), fileInputStream));
                })
                .toList();

        return tupleLocalStorageFiles.stream()
                .map(tupleLocalStorageFile -> new AttachmentEntity()
                        .setFileName(tupleLocalStorageFile.fileName)
                        .setFileEntity(tupleLocalStorageFile.localStorageFile))
                .map(crudAttachmentService::save)
                .toList();
    }

    private static @NotNull String getFileName(@NotNull MultipartFile file)
    {
        return FilenameUtils.getBaseName(file.getOriginalFilename()).replace(" ", "_");
    }

    private record TupleFileNameWithLocalStorageFile(@NotNull String fileName,
                                                     @NotNull LocalStorageFileEntity localStorageFile)
    {
    }
}
