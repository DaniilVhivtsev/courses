package com.fitness.courses.http.objectStorage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.objectStorage.model.entity.LocalStorageFileEntity;

@Repository
public interface LocalStorageFileEntityRepository extends JpaRepository<LocalStorageFileEntity, Long>
{
    Optional<LocalStorageFileEntity> findByBucketNameAndFileKey(String bucketName, String fileKey);
}
