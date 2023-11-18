package com.fitness.courses.utils;

import static com.fitness.courses.http.authorization.AuthorizationConstants.USER_EMAIL;
import static com.fitness.courses.http.authorization.AuthorizationConstants.USER_FIRST_NAME;
import static com.fitness.courses.http.authorization.AuthorizationConstants.USER_LAST_NAME;
import static com.fitness.courses.http.authorization.AuthorizationConstants.USER_PASSWORD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fitness.courses.http.auth.dto.JwtResponse;
import com.fitness.courses.http.auth.dto.LoginRequest;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;
import com.fitness.courses.http.authorization.DSLAuthorization;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.service.UserService;

@Service
public class AuthenticateService
{
    private final UserService userService;

    @Autowired
    public AuthenticateService(UserService userService)
    {
        this.userService = userService;
    }

    public JwtResponse createNewUserAndAuthenticate(DSLAuthorization dslAuthorization) throws JsonProcessingException
    {
        RegistrationUserInfoDto newUserInfo = new RegistrationUserInfoDto();
        newUserInfo.setEmail(USER_EMAIL);
        newUserInfo.setFirstName(USER_FIRST_NAME);
        newUserInfo.setLastName(USER_LAST_NAME);
        newUserInfo.setPassword(USER_PASSWORD);
        dslAuthorization.registration(newUserInfo).getBodyIfStatusOk();

        User userFromDB = userService.findByEmail(USER_EMAIL)
                .orElseThrow(() -> new RuntimeException("Can't find user by email " + USER_EMAIL));

        userFromDB.setConfirmed(true);
        userService.save(userFromDB);

        LoginRequest correctLoginCredentials = new LoginRequest();
        correctLoginCredentials.setLogin(USER_EMAIL);
        correctLoginCredentials.setPassword(USER_PASSWORD);
        return (JwtResponse)dslAuthorization.authenticate(correctLoginCredentials).getBodyIfStatusOk();
    }
}
