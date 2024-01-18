package com.fitness.courses.http.user.dto;

public class AttachmentDto
{
    private Long id;
    private String title;
    private String link;

    public Long getId()
    {
        return id;
    }

    public AttachmentDto setId(Long id)
    {
        this.id = id;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public AttachmentDto setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public String getLink()
    {
        return link;
    }

    public AttachmentDto setLink(String link)
    {
        this.link = link;
        return this;
    }
}
