package com.fitness.courses.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.global.exceptions.ResponseErrorException;
import com.fitness.courses.http.massenger.model.dto.ChatInfoDto;
import com.fitness.courses.http.massenger.model.dto.ChatListInfoDto;
import com.fitness.courses.http.massenger.model.dto.InputMessageDto;
import com.fitness.courses.http.massenger.model.dto.OutputMessageDto;
import com.fitness.courses.http.massenger.service.chat.RestChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Rest контроллер связанный с работой мессенджера: отправкой и получения писем, получения информации о чате/ах,
 * создания чата.
 */
@Tag(
        name = "Контроллер чата.",
        description = "Rest контроллер связанный с работой мессенджера: отправкой и получения писем, получения "
                + "информации о чате/ах, создания чата."
)
@RestController
@RequestMapping("/chat")
public class ChatController
{
    private final RestChatService restChatService;

    @Autowired
    public ChatController(RestChatService restChatService)
    {
        this.restChatService = restChatService;
    }

    @Operation(
            summary = "Get метод получения информации о чате по идентификатору получателя.",
            description = "Get метод получения информации о чате по идентификатору получателя."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о чате успешно возвращена.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ChatInfoDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ошибки отсутствия чата с переданным получателем."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/info/by/recipient/{recipientId}")
    public ResponseEntity<?> getChatByRecipientId(
            @PathVariable @Parameter(description = "Идентификатор пользователя.") Long recipientId)
    {
        try
        {
            return new ResponseEntity<ChatInfoDto>(
                    restChatService.getChatInfoByRecipientId(recipientId),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get метод получения информации о чате по его идентификатору.",
            description = "Get метод получения информации о чате по его идентификатору."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация о чате успешно возвращена.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ChatInfoDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ошибки отсутствия чата с переданным идентификатором."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/info/by/id/{chatId}")
    public ResponseEntity<?> getChatInfoById(
            @PathVariable @Parameter(description = "Идентификатор курса.") Long chatId)
    {
        try
        {
            return new ResponseEntity<ChatInfoDto>(
                    restChatService.getChatInfoById(chatId),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get метод получения информации о чатах текущего пользователя.",
            description = "Get метод получения информации о чатах текущего пользователя."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список чатов с информацией успешно возвращен.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = ChatListInfoDto.class
                                            )
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/get/all/info/by/current/user")
    public ResponseEntity<?> getCurrentUserChatsInfo()
    {
        try
        {
            return new ResponseEntity<List<ChatListInfoDto>>(
                    restChatService.getCurrentUserChatsInfo(),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Post метод создания нового чата с переданным получателем.",
            description = "Post метод создания нового чата с переданным получателем."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Новый чат успешно создан и успешно возвращен",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ChatInfoDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/create/with/recipient/{recipientId}")
    public ResponseEntity<?> createChat(
            @PathVariable @Parameter(description = "Идентификатор пользователя.") Long recipientId
    )
    {
        try
        {
            return new ResponseEntity<ChatInfoDto>(
                    restChatService.createChat(recipientId),
                    HttpStatus.OK
            );
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @MessageMapping("/{chatId}/send/message")
    @SendTo("/chat/{chatId}/push/message")
    public OutputMessageDto executeMessage(@DestinationVariable long chatId, InputMessageDto inputMessageDto, Principal userPrincipal)
    {
        return restChatService.createMessage(chatId, inputMessageDto, userPrincipal);
    }

    // Обновление списка через websocket
}
