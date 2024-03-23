package com.fitness.courses.http.massenger.service.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.service.crud.AbstractCrudService;
import com.fitness.courses.http.massenger.model.entity.MessageEntity;
import com.fitness.courses.http.massenger.repository.MessageEntityRepository;

@Service
public class CrudMessageServiceImpl extends AbstractCrudService<MessageEntity, MessageEntityRepository>
        implements CrudMessageService
{
    @Autowired
    public CrudMessageServiceImpl(MessageEntityRepository repository)
    {
        super(repository, MessageEntity.class);
    }

    @Override
    public List<MessageEntity> findAllByChatEntityIdAndOrderByDateTimeAsc(Long chatEntityId)
    {
        return repository.findAllByChatEntityIdOrderByDateTimeAsc(chatEntityId);
    }
}
