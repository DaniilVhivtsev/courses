package com.fitness.courses.http.massenger.service.message;

import com.fitness.courses.http.massenger.model.dto.MessageInfoDto;
import com.fitness.courses.http.massenger.model.dto.OutputMessageDto;
import com.fitness.courses.http.massenger.model.entity.MessageEntity;

public final class MessageMapper
{
    private MessageMapper()
    {
    }

    public static MessageInfoDto toInfoDto(MessageEntity entity, Long currentUserId)
    {
        final MessageInfoDto infoDto = new MessageInfoDto();
        infoDto.setId(entity.getId());
        infoDto.setDateTime(entity.getDateTime());
        infoDto.setSenderId(entity.getId());
        infoDto.setByCurrentUser(entity.getId().equals(currentUserId));
        infoDto.setTextMessage(entity.getTextMessage());

        return infoDto;
    }

    public static OutputMessageDto toOutputMessageDto(MessageEntity messageEntity)
    {
        final OutputMessageDto dto = new OutputMessageDto();
        dto.setId(messageEntity.getId());
        dto.setTextMessage(messageEntity.getTextMessage());
        dto.setSenderId(messageEntity.getInterlocutorId());
        dto.setDateTime(messageEntity.getDateTime());

        return dto;
    }
}
