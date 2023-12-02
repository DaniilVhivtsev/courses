package com.fitness.courses.http.objectStorage.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.objectStorage.model.entity.LocalStorageFileEntity;

public interface CrudLocalStorageFileEntityService
{
    LocalStorageFileEntity save(LocalStorageFileEntity entity);

    void update(LocalStorageFileEntity entity);

    void delete(@NotNull Long id);

    Optional<LocalStorageFileEntity> getById(@NotNull Long id);

    @Transactional(readOnly = true)
    Optional<LocalStorageFileEntity> getByBucketNameAndFileKey(@NotNull String bucketName,
            @NotNull String fileKey);
}
