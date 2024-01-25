package com.fitness.courses.http.objectStorage.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.objectStorage.model.entity.LocalStorageFileEntity;
import com.fitness.courses.http.objectStorage.repository.LocalStorageFileEntityRepository;

@Service
public class CrudLocalStorageFileEntityServiceImpl implements CrudLocalStorageFileEntityService
{
    private final LocalStorageFileEntityRepository repository;

    @Autowired
    public CrudLocalStorageFileEntityServiceImpl(
            LocalStorageFileEntityRepository repository)
    {
        this.repository = repository;
    }

    @Override
    @Transactional
    public LocalStorageFileEntity save(@NotNull LocalStorageFileEntity entity)
    {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void update(@NotNull LocalStorageFileEntity entity)
    {
        this.save(entity);
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id)
    {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LocalStorageFileEntity> getById(@NotNull Long id)
    {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LocalStorageFileEntity> getByBucketNameAndFileKey(@NotNull String bucketName,
            @NotNull String fileKey)
    {
        return repository.findByBucketNameAndFileKey(bucketName, fileKey);
    }
}
