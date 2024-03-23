package com.fitness.courses.http.massenger.service.message;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.massenger.model.entity.ChatEntity;
import com.fitness.courses.http.massenger.model.entity.MessageEntity;

@Service
public class MessageServiceImpl implements MessageService
{
    private final CrudMessageServiceImpl crudMessageService;

    @Autowired
    public MessageServiceImpl(CrudMessageServiceImpl crudMessageService)
    {
        this.crudMessageService = crudMessageService;
    }

    @Override
    public List<MessageEntity> findAllByChatEntityIdAndOrderByDateTimeAsc(Long chatEntityId)
    {
        return crudMessageService.findAllByChatEntityIdAndOrderByDateTimeAsc(chatEntityId);
    }

    @Override
    public MessageEntity createNew(String textMessage, Long interlocutorId, ChatEntity chatEntity)
    {
        final MessageEntity newMessageEntity = new MessageEntity();
        newMessageEntity.setTextMessage(textMessage);
        newMessageEntity.setInterlocutorId(interlocutorId);
        newMessageEntity.setDateTime(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())));
        newMessageEntity.setChatEntity(chatEntity);

        return crudMessageService.save(newMessageEntity);
    }
}
