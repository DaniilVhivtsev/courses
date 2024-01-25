package com.fitness.courses.http.attachment.model.dto;

public class AttachmentInfoDto
{
    private Long id;

    private String fileName;

    // Url to get with validate permissions
    private String url;

    public Long getId()
    {
        return id;
    }

    public AttachmentInfoDto setId(Long id)
    {
        this.id = id;
        return this;
    }

    public String getFileName()
    {
        return fileName;
    }

    public AttachmentInfoDto setFileName(String fileName)
    {
        this.fileName = fileName;
        return this;
    }

    public String getUrl()
    {
        return url;
    }

    public AttachmentInfoDto setUrl(String url)
    {
        this.url = url;
        return this;
    }
}
