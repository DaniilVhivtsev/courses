package com.fitness.courses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.courses.global.exceptions.ResponseErrorException;
import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequestDto;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;
import com.fitness.courses.http.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Rest контроллер связанный с авторизацией, аутентификацией, регистрацией и обновлением refresh токена.
 */
@Tag(
        name = "Контроллер авторизации.",
        description = "Rest контроллер связанный с авторизацией, аутентификацией, регистрацией и обновлением refresh "
                + "токена."
)
@RestController
@RequestMapping(
        path = "/auth",
        produces = "application/json"
)
public class AuthController
{
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @Operation(
            summary = "Post метод аутентификации пользователя.",
            description = "Post метод аутентификации пользователя позволяет пройти аутентификацию и получить access и"
                    + " refresh токены."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно прошел аутентификацию.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = JwtResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибки валидации",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Ошибка отсутствия пользователя с переданным email.",
                                                    description = "Пользователь с переданным email не найден.",
                                                    value = "User with email doesn't exist"
                                            ),
                                            @ExampleObject(
                                                    name = "Ошибка учетных данных пользователя.",
                                                    description = "Невозможно найти пользователя по логину или "
                                                            + "переданный пароль неверный.",
                                                    value = "Can't find user by login or password is incorrect"
                                            ),
                                            @ExampleObject(
                                                    name = "Ошибка не подтвержден email пользователя.",
                                                    description = "Email пользователя не подтвержден",
                                                    value = "User email isn't confirmed"
                                            ),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<?> authentication(@RequestBody LoginRequestDto loginRequestDto)
    {
        try
        {
            return new ResponseEntity<JwtResponse>(authService.login(loginRequestDto), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Post метод регистрации пользователя.",
            description = "Post метод регистрирует пользователя в системе и отправляет на email письмо с ссылкой на "
                    + "подтверждение почты пользователя."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно зарегистрирован.",
                            content = {}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибки валидации",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Ошибка существования пользователя с переданным email.",
                                                    description = "Пользователь с переданным email уже существует.",
                                                    value = "User with email is already exist"
                                            ),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @PostMapping(value = "/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationUserInfoDto registrationUserInfoDto)
    {
        try
        {
            authService.registration(registrationUserInfoDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Post метод обновления токенов.",
            description = "Post метод обновляет access и refresh токены пользователя."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Токены успешно обновлены.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = JwtResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибки валидации",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Ошибка отсутствия refresh токена в локальном хранилище.",
                                                    description = "Ошибка невозможности получения refresh токена из "
                                                            + "локального хранилища по логину из переданного refresh "
                                                            + "токена.",
                                                    value = "Can't find refresh token by login"
                                            ),
                                            @ExampleObject(
                                                    name = "Ошибка переданный токен не совпадает с действующим.",
                                                    description = "Ошибка переданный refresh токен не совпадает с "
                                                            + "действующим токеном пользователя",
                                                    value = "Refresh token isn't current."
                                            ),
                                            @ExampleObject(
                                                    name = "Ошибка не валидности переданного refresh токена.",
                                                    description = "Ошибка не валидности переданного refresh токена.",
                                                    value = "Refresh token isn't valid"
                                            ),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @PostMapping(value = "/refreshtoken", consumes = "application/json")
    public ResponseEntity<?> refreshTokens(@RequestBody String refreshToken)
    {
        try
        {
            return new ResponseEntity<JwtResponse>(authService.refresh(refreshToken), HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }

    @Operation(
            summary = "Post метод подтверждения почты.",
            description = "Post метод подтверждает эл. почту пользователя по переданным двум кодам."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Почта успешно подтверждена.",
                            content = {}
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @PostMapping("/verifyEmail")
    public ResponseEntity<?> verificationEmail(@RequestParam String code, @RequestParam String secondeCode)
    {
        try
        {
            authService.confirmEmailByCode(secondeCode, code);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (ResponseErrorException e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.valueOf(e.getHttpStatusCode()));
        }
    }
}
