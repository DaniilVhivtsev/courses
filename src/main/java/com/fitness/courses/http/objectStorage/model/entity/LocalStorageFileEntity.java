package com.fitness.courses.http.objectStorage.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "local_storage_file_entity",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "fileKey",
                                "bucketName"
                        }
                )
        }
)
public class LocalStorageFileEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String fileKey;

    @Column(nullable = false)
    private String bucketName;

    @Column(nullable = false)
    private ContentTypeEnum contentType;

    @Column(nullable = false)
    private FileExtensionEnum fileExtension;

    @Column(nullable = false)
    private String url;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFileKey()
    {
        return fileKey;
    }

    public void setFileKey(String fileKey)
    {
        this.fileKey = fileKey;
    }

    public String getBucketName()
    {
        return bucketName;
    }

    public void setBucketName(String bucketName)
    {
        this.bucketName = bucketName;
    }

    public ContentTypeEnum getContentType()
    {
        return contentType;
    }

    public void setContentType(ContentTypeEnum contentType)
    {
        this.contentType = contentType;
    }

    public FileExtensionEnum getFileExtension()
    {
        return fileExtension;
    }

    public void setFileExtension(FileExtensionEnum fileExtension)
    {
        this.fileExtension = fileExtension;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
