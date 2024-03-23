package com.fitness.courses.http.massenger.model.dto;

import java.util.List;

public class ChatInfoDto
{
    private Long id;

    private String title;

    private String recipientLogoUrl;

    private Long recipientId;

    private List<MessageInfoDto> messages;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getRecipientLogoUrl()
    {
        return recipientLogoUrl;
    }

    public void setRecipientLogoUrl(String recipientLogoUrl)
    {
        this.recipientLogoUrl = recipientLogoUrl;
    }

    public Long getRecipientId()
    {
        return recipientId;
    }

    public void setRecipientId(Long recipientId)
    {
        this.recipientId = recipientId;
    }

    public List<MessageInfoDto> getMessages()
    {
        return messages;
    }

    public void setMessages(List<MessageInfoDto> messages)
    {
        this.messages = messages;
    }
}
