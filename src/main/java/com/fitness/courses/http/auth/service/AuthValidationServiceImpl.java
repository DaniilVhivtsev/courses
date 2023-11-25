package com.fitness.courses.http.auth.service;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ConflictException;
import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.auth.dto.LoginRequestDto;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.service.UserService;

@Service
public class AuthValidationServiceImpl implements AuthValidationService
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(AuthValidationServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthValidationServiceImpl(
            AuthenticationManager authenticationManager,
            UserService userService)
    {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public void validateUserLoginCredentials(@NotNull LoginRequestDto loginRequestDto) throws ValidationException
    {
        Authentication authentication = null;
        try
        {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getLogin(),
                            loginRequestDto.getPassword()));
        }
        catch (AuthenticationException e)
        {
            LOG.error(e.getLocalizedMessage());
        }

        if (authentication == null || !authentication.isAuthenticated())
        {
            final String message = "Can't find user by login or password is incorrect";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override // TODO to userValidationService
    public void validateUserExistByEmail(String email) throws ValidationException
    {
        if (userService.findByEmail(email).isEmpty())
        {
            final String message = "User with email doesn't exist";
            final String messageForLog = "User with email %s doesn't exist".formatted(email);
            LOG.error(messageForLog);
            throw new ValidationException(message);
        }
    }

    @Override // TODO to userValidationService
    public void validateUserIsNotExistByEmail(String email) throws ValidationException
    {
        if (userService.findByEmail(email).isPresent())
        {
            final String message = "User with email is already exist";
            final String messageForLog = "User with email %s is already exist".formatted(email);
            LOG.error(messageForLog);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateUserEmailIsConfirmed(String email) throws ValidationException
    {
        final User userFromDB = userService.findByEmail(email).get();

        if (!userFromDB.isConfirmed())
        {
            final String message = "User email isn't confirmed";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
