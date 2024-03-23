package com.fitness.courses.http.massenger.service.message;

import java.util.List;

import com.fitness.courses.http.massenger.model.entity.MessageEntity;

public interface CrudMessageService
{
    List<MessageEntity> findAllByChatEntityIdAndOrderByDateTimeAsc(Long chatEntityId);
}
