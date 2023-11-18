package com.fitness.courses.http.auth.service;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequest;
import com.fitness.courses.http.auth.dto.RefreshTokenDto;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;

import jakarta.mail.MessagingException;

public interface AuthService
{
    @NotNull JwtResponse login(@NotNull LoginRequest loginRequest);

    @NotNull JwtResponse refresh(@NotNull String refreshToken);

    void registration(@NotNull RegistrationUserInfoDto registrationUserInfoDto);

    void confirmEmailByCode(String userIdString, String confirmCode);
}
