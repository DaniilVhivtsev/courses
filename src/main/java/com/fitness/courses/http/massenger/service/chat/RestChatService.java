package com.fitness.courses.http.massenger.service.chat;

import java.security.Principal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fitness.courses.http.massenger.model.dto.ChatInfoDto;
import com.fitness.courses.http.massenger.model.dto.ChatListInfoDto;
import com.fitness.courses.http.massenger.model.dto.InputMessageDto;
import com.fitness.courses.http.massenger.model.dto.OutputMessageDto;

public interface RestChatService
{
    ChatInfoDto getChatInfoByRecipientId(@NotNull Long recipientId);

    ChatInfoDto getChatInfoById(@NotNull Long chatId);

    List<ChatListInfoDto> getCurrentUserChatsInfo();

    ChatInfoDto createChat(@Nullable Long recipientId);

    OutputMessageDto createMessage(Long chatId, InputMessageDto inputMessageDto, Principal userPrincipal);
}
