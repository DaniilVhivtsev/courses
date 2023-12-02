package com.fitness.courses.http.objectStorage.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fitness.courses.configuration.s3.bucket.YandexBucketProvider;
import com.fitness.courses.global.utils.UUIDGenerator;
import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;
import com.fitness.courses.http.objectStorage.model.entity.LocalStorageFileEntity;
import com.fitness.courses.http.objectStorage.utils.FileExtensionUtils;
import com.fitness.courses.http.objectStorage.utils.LocalStorageMetadataUtils;

@Service
public class LocalStorageFileServiceImpl implements LocalStorageFileService
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(LocalStorageFileServiceImpl.class);

    private final CrudLocalStorageFileEntityService crudLocalStorageFileEntityService;
    private final YandexBucketProvider yandexBucketProvider;

    public LocalStorageFileServiceImpl(
            CrudLocalStorageFileEntityService crudLocalStorageFileEntityService,
            YandexBucketProvider yandexBucketProvider)
    {
        this.crudLocalStorageFileEntityService = crudLocalStorageFileEntityService;
        this.yandexBucketProvider = yandexBucketProvider;
    }

    @Override
    public LocalStorageFileEntity addFile(String bucketName, FileExtensionEnum fileExtension, InputStream inputStream)
    {
        LocalStorageFileEntity localStorageFileEntity = new LocalStorageFileEntity();
        localStorageFileEntity.setFileKey(UUIDGenerator.nestUuidInString() + ".%s".formatted(fileExtension.getValue())); //
        // TODO check
        localStorageFileEntity.setBucketName(bucketName);
        localStorageFileEntity.setFileExtension(fileExtension);
        localStorageFileEntity.setContentType(FileExtensionUtils.getContentType(fileExtension));

        yandexBucketProvider.putObject(bucketName, localStorageFileEntity.getFileKey(), inputStream,
                LocalStorageMetadataUtils.getObjectMetadata(localStorageFileEntity, inputStream));
        localStorageFileEntity.setUrl(
                yandexBucketProvider.getUrl(bucketName, localStorageFileEntity.getFileKey()).toExternalForm());
        localStorageFileEntity = crudLocalStorageFileEntityService.save(localStorageFileEntity);

        return localStorageFileEntity;
    }

    @Override
    public List<LocalStorageFileEntity> addFiles(String bucketName, List<FileExtensionWithInputStreamRecord> files)
    {
        ExecutorService executorService = Executors.newFixedThreadPool(files.size());
        List<Future<LocalStorageFileEntity>> localStorageFileEntityFutures = new ArrayList<>();
        for (FileExtensionWithInputStreamRecord file : files)
        {
            Future<LocalStorageFileEntity> localStorageFileEntityFuture = executorService.submit(() ->
                    this.addFile(bucketName, file.fileExtensionEnum(), file.inputStream())
            );

            localStorageFileEntityFutures.add(localStorageFileEntityFuture);
        }

        List<LocalStorageFileEntity> localStorageFileEntities = new ArrayList<>();
        // Ожидание завершения задач
        for (Future<LocalStorageFileEntity> future : localStorageFileEntityFutures)
        {
            try
            {
                LocalStorageFileEntity localStorageFileEntity = future.get();
                localStorageFileEntities.add(localStorageFileEntity);
            }
            catch (InterruptedException | ExecutionException e)
            {
                LOG.error(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();

        return localStorageFileEntities;
    }

    @Override
    public FileWithContentRecord getFile(String bucketName, String fileKey)
    {
        LocalStorageFileEntity localStorageFileEntity =
                crudLocalStorageFileEntityService.getByBucketNameAndFileKey(bucketName, fileKey)
                        .orElseThrow();
        InputStream inputStream = yandexBucketProvider.getObject(bucketName, fileKey).getObjectContent();

        return new FileWithContentRecord(localStorageFileEntity, inputStream);
    }

    @Override
    public LocalStorageFileEntity getFileEntity(String bucketName, String fileKey)
    {
        return crudLocalStorageFileEntityService.getByBucketNameAndFileKey(bucketName, fileKey)
                        .orElseThrow();
    }

    public void deleteFile()
    {
    }

    public void deleteFiles()
    {
    }

    public void updateFile()
    {
    }

    public void updateFiles()
    {
    }
}
