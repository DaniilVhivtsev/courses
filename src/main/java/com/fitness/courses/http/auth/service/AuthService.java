package com.fitness.courses.http.auth.service;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequestDto;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;

public interface AuthService
{
    @NotNull JwtResponse login(@NotNull LoginRequestDto loginRequestDto);

    @NotNull JwtResponse refresh(@NotNull String refreshToken);

    void registration(@NotNull RegistrationUserInfoDto registrationUserInfoDto);

    void confirmEmailByCode(String userIdString, String confirmCode);
}
