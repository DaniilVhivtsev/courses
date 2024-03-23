package com.fitness.courses.test.http.chat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.courses.CoursesApplication;
import com.fitness.courses.controller.AuthController;
import com.fitness.courses.controller.ChatController;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;
import com.fitness.courses.http.massenger.model.dto.ChatInfoDto;
import com.fitness.courses.http.massenger.model.dto.InputMessageDto;
import com.fitness.courses.http.massenger.model.dto.OutputMessageDto;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.service.UserService;
import com.fitness.courses.test.http.authorization.DSLAuthorization;
import com.fitness.courses.test.utils.AuthenticateService;

@SpringBootTest(classes = CoursesApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ChatTest
{
    private final int port;
    private final DSLAuthorization dslAuthorization;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final AuthenticateService authenticateService;
    private final AuthController authController;
    private final ChatController chatController;

    @Autowired
    public ChatTest(
            @LocalServerPort int port,
            UserService userService,
            ObjectMapper objectMapper,
            AuthenticateService authenticateService,
            AuthController authController,
            ChatController chatController)
    {
        this.port = port;
        this.dslAuthorization = new DSLAuthorization(port, objectMapper);
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.authenticateService = authenticateService;
        this.authController = authController;
        this.chatController = chatController;

        HttpStatusCode httpStatusCode = authController.registration(
                new RegistrationUserInfoDto()
                        .setEmail("danya.vshivtsev@gmail.com")
                        .setFirstName("Daniil")
                        .setLastName("Vshivtsev")
                        .setPassword("12345")
        ).getStatusCode();

        User currentUser = userService.findByEmail("danya.vshivtsev@gmail.com").orElseThrow();
        userService.update(currentUser.setConfirmed(true));

        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                currentUser,
                null,
                currentUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @BeforeAll
    static void setUp()
    {
    }

    @Test
    void createChat()
    {
        HttpStatusCode httpStatusCode = authController.registration(
                new RegistrationUserInfoDto()
                        .setEmail("danya.vshivtsev@mail.ru")
                        .setFirstName("Daniil")
                        .setLastName("Vshivtsev")
                        .setPassword("12345")
        ).getStatusCode();
        Assertions.assertTrue(httpStatusCode.is2xxSuccessful());

        User currentUser = userService.findByEmail("danya.vshivtsev@mail.ru").orElseThrow();
        ResponseEntity<?> response = chatController.createChat(currentUser.getId());
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        ChatInfoDto chat = (ChatInfoDto)response.getBody();


        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                currentUser,
                null,
                currentUser.getAuthorities()
        );

        InputMessageDto inputMessageDto = new InputMessageDto();
        inputMessageDto.setTextMessage("Hello world");
        OutputMessageDto outputMessageDto = chatController.executeMessage(chat.getId(), inputMessageDto, authentication);

        response = chatController.getChatInfoById(chat.getId());
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        response = chatController.getCurrentUserChatsInfo();
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @AfterEach
    void afterEach()
    {
    }

    @AfterAll
    static void afterAll()
    {
    }
}
