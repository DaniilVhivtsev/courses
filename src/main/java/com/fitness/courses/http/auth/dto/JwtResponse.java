package com.fitness.courses.http.auth.dto;

public class JwtResponse
{
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;

    public String getType()
    {
        return type;
    }

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
