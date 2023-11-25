package com.fitness.courses.http.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ сервера с Jwt токенами.")
public class JwtResponse
{
    @Schema(description = "Тип токена")
    public static final String TYPE = "Bearer";

    @Schema(description = "Access токен.")
    private String accessToken;

    @Schema(description = "Refresh токен.")
    private String refreshToken;

    public String getAccessToken()
    {
        return accessToken;
    }

    public JwtResponse setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public JwtResponse setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
        return this;
    }
}
