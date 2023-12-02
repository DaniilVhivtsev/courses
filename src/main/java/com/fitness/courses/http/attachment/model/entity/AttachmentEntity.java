package com.fitness.courses.http.attachment.model.entity;

import com.fitness.courses.http.objectStorage.model.entity.LocalStorageFileEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attachment_entity")
public class AttachmentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @OneToOne
    @JoinColumn(name = "file_entity_id")
    private LocalStorageFileEntity fileEntity;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFileName()
    {
        return fileName;
    }

    public AttachmentEntity setFileName(String fileName)
    {
        this.fileName = fileName;
        return this;
    }

    public LocalStorageFileEntity getFileEntity()
    {
        return fileEntity;
    }

    public AttachmentEntity setFileEntity(LocalStorageFileEntity fileEntity)
    {
        this.fileEntity = fileEntity;
        return this;
    }
}
