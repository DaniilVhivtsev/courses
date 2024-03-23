package com.fitness.courses.http.massenger.model.dto;

public class ChatListInfoDto
{
    private Long id;

    private String title;

    private String recipientLogoUrl;

    private Long recipientId;

    private String lastMessageText;

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

    public String getLastMessageText()
    {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText)
    {
        this.lastMessageText = lastMessageText;
    }
}
