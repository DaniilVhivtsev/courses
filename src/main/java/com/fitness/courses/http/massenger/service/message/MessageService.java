package com.fitness.courses.http.massenger.service.message;

import java.util.List;

import com.fitness.courses.http.massenger.model.entity.ChatEntity;
import com.fitness.courses.http.massenger.model.entity.MessageEntity;

public interface MessageService
{
    List<MessageEntity> findAllByChatEntityIdAndOrderByDateTimeAsc(Long chatEntityId);

    MessageEntity createNew(String textMessage, Long interlocutorId, ChatEntity chatEntity);
}
