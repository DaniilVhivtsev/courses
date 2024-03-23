package com.fitness.courses.http.massenger.service.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.massenger.model.dto.ChatInfoDto;
import com.fitness.courses.http.massenger.model.dto.ChatListInfoDto;
import com.fitness.courses.http.massenger.model.entity.ChatEntity;
import com.fitness.courses.http.massenger.model.entity.MessageEntity;
import com.fitness.courses.http.massenger.service.message.MessageMapper;
import com.fitness.courses.http.user.model.User;

public final class ChatMapper
{
    private ChatMapper()
    {
    }

    public static ChatInfoDto toInfoDto(ChatEntity chatEntity, List<MessageEntity> messages, User recipient,
            Long currentUserId)
    {
        ChatInfoDto dto = new ChatInfoDto();
        dto.setId(chatEntity.getId());
        dto.setTitle(recipient.getFullName());
        if (recipient.getLogo() != null)
        {
            dto.setRecipientLogoUrl(recipient.getLogo().getFileEntity().getUrl());
        }
        dto.setRecipientId(recipient.getId());
        if (messages != null)
        {
            dto.setMessages(messages.stream().map(message -> MessageMapper.toInfoDto(message, currentUserId)).toList());
        }
        else
        {
            dto.setMessages(new ArrayList<>());
        }

        return dto;
    }

    public static ChatListInfoDto toListInfoDto(ChatEntity entity, User recipient, @Nullable String lastMessageText)
    {
        ChatListInfoDto listInfoDto = new ChatListInfoDto();
        listInfoDto.setId(entity.getId());
        listInfoDto.setTitle(recipient.getFullName());
        listInfoDto.setRecipientId(recipient.getId());
        if (recipient.getLogo() != null)
        {
            listInfoDto.setRecipientLogoUrl(recipient.getLogo().getFileEntity().getUrl());
        }

        listInfoDto.setLastMessageText(lastMessageText != null ? lastMessageText : "");

        return listInfoDto;
    }
}
