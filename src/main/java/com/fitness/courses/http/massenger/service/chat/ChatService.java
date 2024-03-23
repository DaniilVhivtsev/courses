package com.fitness.courses.http.massenger.service.chat;

import java.util.Optional;
import java.util.Set;

import com.fitness.courses.http.massenger.model.entity.ChatEntity;
import com.fitness.courses.http.user.model.User;

public interface ChatService
{
    Optional<ChatEntity> findChatWithInterlocutorsIds(Set<Long> interlocutorsIds);

    ChatEntity findChatByIdOrThrow(Long chatId);

    Optional<ChatEntity> findChatById(Long chatId);

    User getRecipient(Long chatId, Long senderId);

    Set<ChatEntity> findAllChatsByInterlocutorId(Long interlocutorId);

    User getRecipientOrThrow(Set<Long> interlocutorsIds, Long currentUserId);

    ChatEntity createNewChat(Set<Long> interlocutorsIds);
}
