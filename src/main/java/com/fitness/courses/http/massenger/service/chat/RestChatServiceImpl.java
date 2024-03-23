package com.fitness.courses.http.massenger.service.chat;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.massenger.model.dto.ChatInfoDto;
import com.fitness.courses.http.massenger.model.dto.ChatListInfoDto;
import com.fitness.courses.http.massenger.model.dto.InputMessageDto;
import com.fitness.courses.http.massenger.model.dto.OutputMessageDto;
import com.fitness.courses.http.massenger.model.entity.ChatEntity;
import com.fitness.courses.http.massenger.model.entity.MessageEntity;
import com.fitness.courses.http.massenger.service.message.MessageMapper;
import com.fitness.courses.http.massenger.service.message.MessageService;
import com.fitness.courses.http.massenger.service.message.MessageValidator;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.service.UserService;
import com.fitness.courses.http.user.service.UserValidator;

@Service
public class RestChatServiceImpl implements RestChatService
{
    private final ChatService chatService;
    private final ChatValidator chatValidator;
    private final AuthService authService;
    private final MessageService messageService;
    private final UserValidator userValidator;
    private final UserService userService;
    private final MessageValidator messageValidator;

    @Autowired
    public RestChatServiceImpl(
            ChatService chatService,
            ChatValidator chatValidator,
            AuthService authService,
            MessageService messageService,
            UserValidator userValidator,
            UserService userService,
            MessageValidator messageValidator)
    {
        this.chatService = chatService;
        this.chatValidator = chatValidator;
        this.authService = authService;
        this.messageService = messageService;
        this.userValidator = userValidator;
        this.userService = userService;
        this.messageValidator = messageValidator;
    }

    @Override
    public ChatInfoDto getChatInfoByRecipientId(@Nullable Long recipientId)
    {
        Objects.requireNonNull(recipientId);

        final Long currentUserId = authService.getCurrentUserOrThrow().getId();
        final Set<Long> interlocutorsIds = Set.of(recipientId, currentUserId);
        chatValidator.validateChatExistWithInterlocutorsIds(interlocutorsIds);

        final ChatEntity chatEntity = chatService.findChatWithInterlocutorsIds(interlocutorsIds).orElseThrow();
        final List<MessageEntity> messages =
                messageService.findAllByChatEntityIdAndOrderByDateTimeAsc(chatEntity.getId());

        return ChatMapper.toInfoDto(chatEntity, messages, chatService.getRecipient(chatEntity.getId(), currentUserId),
                currentUserId);
    }

    @Override
    public ChatInfoDto getChatInfoById(@Nullable Long chatId)
    {
        Objects.requireNonNull(chatId);
        chatValidator.validateChatExist(chatId);

        final Long currentUserId = authService.getCurrentUserOrThrow().getId();

        final ChatEntity chatEntity = chatService.findChatByIdOrThrow(chatId);
        final List<MessageEntity> messages =
                messageService.findAllByChatEntityIdAndOrderByDateTimeAsc(chatEntity.getId());

        return ChatMapper.toInfoDto(chatEntity, messages, chatService.getRecipient(chatEntity.getId(), currentUserId),
                currentUserId);
    }

    @Override
    public List<ChatListInfoDto> getCurrentUserChatsInfo()
    {
        final Long currentUserId = authService.getCurrentUserOrThrow().getId();
        return chatService.findAllChatsByInterlocutorId(currentUserId).stream()
                .map(chatEntity ->
                {
                    final List<MessageEntity> messages =
                            messageService.findAllByChatEntityIdAndOrderByDateTimeAsc(chatEntity.getId());
                    final String lastMessageDto = messages.isEmpty() // Nullable
                            ? null
                            : messages.get(messages.size() - 1).getTextMessage();
                    return ChatMapper.toListInfoDto(chatEntity,
                            chatService.getRecipientOrThrow(new HashSet<>(chatEntity.getInterlocutorsIds()), currentUserId),
                            lastMessageDto);
                })
                .toList();
    }

    @Override
    public ChatInfoDto createChat(@Nullable Long recipientId)
    {
        Objects.requireNonNull(recipientId);
        userValidator.validateUserExist(recipientId);

        final Long currentUserId = authService.getCurrentUserOrThrow().getId();
        final Set<Long> interlocutorsIds = new HashSet<>(Set.of(currentUserId, recipientId));
        chatValidator.validateChatNotExistWithInterlocutorsIds(interlocutorsIds);

        final ChatEntity newChat = chatService.createNewChat(interlocutorsIds);

        return ChatMapper.toInfoDto(
                newChat, new ArrayList<>(), userService.findByIdOrThrow(recipientId), currentUserId
        );
    }

    @Override
    public OutputMessageDto createMessage(Long chatId, InputMessageDto inputMessageDto, Principal userPrincipal)
    {
        Objects.requireNonNull(chatId);
        chatValidator.validateChatExist(chatId);
        messageValidator.validateTextMessage(inputMessageDto.getTextMessage());

        Objects.requireNonNull(userPrincipal);
        final User user = (User)((Authentication) userPrincipal).getPrincipal();
        final Long currentUserId = user.getId();
        chatValidator.validateUserHasPermissions(chatId, currentUserId);

        final ChatEntity chatEntity = chatService.findChatByIdOrThrow(chatId);

        final MessageEntity newMessageEntity = messageService.createNew(inputMessageDto.getTextMessage(), currentUserId,
                chatEntity);

        return MessageMapper.toOutputMessageDto(newMessageEntity);
    }
}
