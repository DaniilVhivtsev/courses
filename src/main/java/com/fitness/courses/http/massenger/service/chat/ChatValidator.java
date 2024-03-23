package com.fitness.courses.http.massenger.service.chat;

import java.util.Set;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.global.exceptions.ValidationException;

public interface ChatValidator
{
    void validateChatExistWithInterlocutorsIds(Set<Long> interlocutorsIds) throws NotFoundException;

    void validateChatNotExistWithInterlocutorsIds(Set<Long> interlocutorsIds) throws ValidationException;

    void validateChatExist(Long chatId) throws NotFoundException;

    void validateUserHasPermissions(Long chatId, Long currentUserId) throws ValidationException;
}
