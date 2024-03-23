package com.fitness.courses.http.massenger.service.chat;

import java.util.Optional;
import java.util.Set;

import com.fitness.courses.http.massenger.model.entity.ChatEntity;

public interface CrudChatService
{
    Optional<ChatEntity> findChatWithInterlocutorsIds(Set<Long> interlocutorsIds);

    Set<ChatEntity> findByInterlocutorId(Long interlocutorId);
}
