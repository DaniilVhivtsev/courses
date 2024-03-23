package com.fitness.courses.http.massenger.service.chat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.service.crud.AbstractCrudService;
import com.fitness.courses.http.massenger.model.entity.ChatEntity;
import com.fitness.courses.http.massenger.repository.ChatEntityRepository;

@Service
public class CrudChatServiceImpl extends AbstractCrudService<ChatEntity, ChatEntityRepository>
        implements CrudChatService
{
    @Autowired
    public CrudChatServiceImpl(ChatEntityRepository repository)
    {
        super(repository, ChatEntity.class);

    }

    @Override
    public Optional<ChatEntity> findChatWithInterlocutorsIds(Set<Long> interlocutorsIds)
    {
        return repository.findChatEntityByInterlocutorsIds(new ArrayList<>(interlocutorsIds));
    }

    @Override
    public Set<ChatEntity> findByInterlocutorId(Long interlocutorId)
    {
        return new HashSet<>(repository.findByInterlocutorId(interlocutorId));
    }
}
