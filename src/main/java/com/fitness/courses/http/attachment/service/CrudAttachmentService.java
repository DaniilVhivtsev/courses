package com.fitness.courses.http.attachment.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;

public interface CrudAttachmentService
{
    @Transactional
    AttachmentEntity save(@NotNull AttachmentEntity entity);

    @Transactional
    void update(@NotNull AttachmentEntity entity);

    @Transactional
    void delete(@NotNull Long id);

    @Transactional(readOnly = true)
    Optional<AttachmentEntity> getById(@NotNull Long id);
}
