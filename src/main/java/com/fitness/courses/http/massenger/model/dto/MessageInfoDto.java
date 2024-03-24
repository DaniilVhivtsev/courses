package com.fitness.courses.http.massenger.model.dto;

import java.util.Date;

public class MessageInfoDto
{
    private Long id;
    private Date dateTime;
    private Long senderId;
    private boolean isByCurrentUser;
    private String textMessage;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(Date dateTime)
    {
        this.dateTime = dateTime;
    }

    public Long getSenderId()
    {
        return senderId;
    }

    public void setSenderId(Long senderId)
    {
        this.senderId = senderId;
    }

    public boolean isByCurrentUser()
    {
        return isByCurrentUser;
    }

    public void setByCurrentUser(boolean byCurrentUser)
    {
        isByCurrentUser = byCurrentUser;
    }

    public String getTextMessage()
    {
        return textMessage;
    }

    public void setTextMessage(String textMessage)
    {
        this.textMessage = textMessage;
    }
}