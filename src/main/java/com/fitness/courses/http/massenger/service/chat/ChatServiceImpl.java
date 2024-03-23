package com.fitness.courses.http.massenger.service.chat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.massenger.model.entity.ChatEntity;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.service.UserService;

@Service
public class ChatServiceImpl implements ChatService
{
    private final CrudChatServiceImpl crudChatService;
    private final UserService userService;

    @Autowired
    public ChatServiceImpl(
            CrudChatServiceImpl crudChatService,
            UserService userService)
    {
        this.crudChatService = crudChatService;
        this.userService = userService;
    }

    @Override
    public Optional<ChatEntity> findChatWithInterlocutorsIds(Set<Long> interlocutorsIds)
    {
        return crudChatService.findChatWithInterlocutorsIds(interlocutorsIds);
    }

    @Override
    public ChatEntity findChatByIdOrThrow(Long chatId)
    {
        return crudChatService.findByIdOrThrow(chatId);
    }

    @Override
    public Optional<ChatEntity> findChatById(Long chatId)
    {
        return crudChatService.findById(chatId);
    }

    @Override
    public User getRecipient(Long chatId, Long senderId)
    {
        Set<Long> interlocutorsIds = findChatByIdOrThrow(chatId).getInterlocutorsIds();
        interlocutorsIds.remove(senderId);

        return interlocutorsIds.stream()
                .findAny()
                .map(userService::findByIdOrThrow)
                .orElseThrow(() -> new NotFoundException("Can't find chat with id %s recipient".formatted(chatId)));
    }

    @Override
    public Set<ChatEntity> findAllChatsByInterlocutorId(Long interlocutorId)
    {
        return crudChatService.findByInterlocutorId(interlocutorId);
    }

    @Override
    public User getRecipientOrThrow(Set<Long> interlocutorsIds, Long currentUserId)
    {
        interlocutorsIds.remove(currentUserId);
        final Long recipientId = interlocutorsIds.stream().findAny().orElseThrow();

        return userService.findByIdOrThrow(recipientId);
    }

    @Override
    public ChatEntity createNewChat(Set<Long> interlocutorsIds)
    {
        final ChatEntity newChat = new ChatEntity();
        newChat.setInterlocutorsIds(interlocutorsIds);

        return crudChatService.save(newChat);
    }
}
