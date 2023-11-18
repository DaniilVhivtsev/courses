package com.fitness.courses.http.auth.dto;

public class JwtResponse
{
    private static final String TYPE = "Bearer";
    private String accessToken;
    private String refreshToken;

    public static String getType()
    {
        return TYPE;
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
