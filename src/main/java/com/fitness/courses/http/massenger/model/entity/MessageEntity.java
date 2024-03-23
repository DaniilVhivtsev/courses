package com.fitness.courses.http.massenger.model.entity;

import java.sql.Timestamp;

import org.joda.time.DateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "message_entity")
public class MessageEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 2048)
    private String textMessage;

    private Long interlocutorId;

    private Timestamp dateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_entity_id", nullable = false)
    private ChatEntity chatEntity;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTextMessage()
    {
        return textMessage;
    }

    public void setTextMessage(String textMessage)
    {
        this.textMessage = textMessage;
    }

    public Long getInterlocutorId()
    {
        return interlocutorId;
    }

    public void setInterlocutorId(Long interlocutorId)
    {
        this.interlocutorId = interlocutorId;
    }

    public Timestamp getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime)
    {
        this.dateTime = dateTime;
    }

    public ChatEntity getChatEntity()
    {
        return chatEntity;
    }

    public void setChatEntity(ChatEntity chatEntity)
    {
        this.chatEntity = chatEntity;
    }
}
