package com.fitness.courses.http.auth.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.global.exceptions.AuthorizedException;
import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequestDto;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;
import com.fitness.courses.http.user.model.User;

public interface AuthService
{
    @NotNull JwtResponse login(@NotNull LoginRequestDto loginRequestDto);

    @NotNull JwtResponse refresh(@NotNull String refreshToken);

    void registration(@NotNull RegistrationUserInfoDto registrationUserInfoDto);

    void confirmEmailByCode(String userIdString, String confirmCode);

    User getCurrentUserOrThrow() throws AuthorizedException;

    Optional<User> getCurrentUser();
}
