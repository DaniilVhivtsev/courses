package com.fitness.courses.http.auth.service;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequest;
import com.fitness.courses.http.auth.dto.RefreshTokenDto;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;

import jakarta.mail.MessagingException;

public interface AuthService
{
    @NotNull JwtResponse login(@NotNull LoginRequest loginRequest) throws MessagingException;

    @NotNull JwtResponse refresh(@NotNull RefreshTokenDto refreshTokenDto);

    void registration(@NotNull RegistrationUserInfoDto registrationUserInfoDto) throws MessagingException;

    void confirmEmailByCode(String userIdString, String confirmCode);
}
