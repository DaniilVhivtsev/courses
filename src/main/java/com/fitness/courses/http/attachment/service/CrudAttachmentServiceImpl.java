package com.fitness.courses.http.attachment.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.http.attachment.model.entity.AttachmentEntity;
import com.fitness.courses.http.attachment.repository.AttachmentRepository;

@Service
public class CrudAttachmentServiceImpl implements CrudAttachmentService
{
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public CrudAttachmentServiceImpl(
            AttachmentRepository attachmentRepository)
    {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    @Transactional
    public AttachmentEntity save(@NotNull AttachmentEntity entity)
    {
        return attachmentRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(@NotNull AttachmentEntity entity)
    {
        this.save(entity);
    }

    @Override
    @Transactional
    public void delete(@NotNull Long id)
    {
        attachmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttachmentEntity> getById(@NotNull Long id)
    {
        return attachmentRepository.findById(id);
    }
}
