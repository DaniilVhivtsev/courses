package com.fitness.courses.http.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Dto с информацией для аутентификации пользователя")
public class LoginRequestDto
{
    @Schema(description = "Login пользователя.")
    private String login;

    @Schema(description = "Password пользователя.")
    private String password;

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
