package com.fitness.courses.http.massenger.model.entity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_id")
public class ChatEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "interlocutors_by users_in_chat_ids", joinColumns = @JoinColumn(name = "chat_id"))
    @Column(name = "interlocutors_ids")
    private Set<Long> interlocutorsIds;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Set<Long> getInterlocutorsIds()
    {
        return interlocutorsIds;
    }

    public void setInterlocutorsIds(Set<Long> interlocutorsIds)
    {
        this.interlocutorsIds = interlocutorsIds;
    }
}
