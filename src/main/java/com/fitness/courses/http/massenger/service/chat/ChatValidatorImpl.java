package com.fitness.courses.http.massenger.service.chat;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.global.exceptions.ValidationException;

@Service
public class ChatValidatorImpl implements ChatValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(ChatValidatorImpl.class);

    private final ChatServiceImpl chatService;

    @Autowired
    public ChatValidatorImpl(ChatServiceImpl chatService)
    {
        this.chatService = chatService;
    }

    @Override
    public void validateChatExistWithInterlocutorsIds(Set<Long> interlocutorsIds) throws NotFoundException
    {
        if (chatService.findChatWithInterlocutorsIds(interlocutorsIds).isEmpty())
        {
            final String message = "Chat doesn't exist with interlocutors ids: %s"
                    .formatted(StringUtils.joinWith(", ", interlocutorsIds));
            LOG.error(message);
            throw new NotFoundException(message);
        }
    }

    @Override
    public void validateChatNotExistWithInterlocutorsIds(Set<Long> interlocutorsIds) throws ValidationException
    {
        if (chatService.findChatWithInterlocutorsIds(interlocutorsIds).isPresent())
        {
            final String message = "Chat already exist with interlocutors ids: %s"
                    .formatted(StringUtils.joinWith(", ", interlocutorsIds));
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateChatExist(Long chatId) throws NotFoundException
    {
        if (chatService.findChatById(chatId).isEmpty())
        {
            final String message = "Chat with id %s doesn't exist".formatted(chatId.toString());
            LOG.error(message);
            throw new NotFoundException(message);
        }
    }

    @Override
    public void validateUserHasPermissions(Long chatId, Long currentUserId) throws ValidationException
    {
        final Set<Long> interlocutorsIds = new HashSet<>(chatService.findChatByIdOrThrow(chatId).getInterlocutorsIds());

        if (!interlocutorsIds.contains(currentUserId))
        {
            final String message = "User with id %s doesn't have permission send to chat with id %s."
                    .formatted(currentUserId.toString(), chatId.toString());
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
