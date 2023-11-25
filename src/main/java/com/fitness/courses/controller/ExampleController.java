package com.fitness.courses.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Rest контроллер для проверки авторизации.
 */
@Tag(
        name = "Контроллер проверки авторизации.",
        description = "Rest контроллер для проверки авторизации."
)
@RestController
@RequestMapping("/")
public class ExampleController
{
    @Operation(
            summary = "Post метод для проверки авторизации.",
            description = "Post метод для проверки авторизации."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос успешно отработал",
                            content = @Content(
                                    mediaType = "text/plain",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Ответ запрос.",
                                                    description = "Пользователь успешно авторизовался.",
                                                    value = "Current user with email ${userEmail}"
                                            ),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Ошибка авторизации пользователя. Пользователь не авторизован или access "
                                    + "токен просрочился."
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Ошибки сервера."
                    ),
            }
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping("/example/authenticated/request")
    public ResponseEntity<?> examplePostMethod(@AuthenticationPrincipal UserDetails userDetails)
    {
        return new ResponseEntity<>("Current user with email " + userDetails.getUsername(), HttpStatus.OK);
    }
}
