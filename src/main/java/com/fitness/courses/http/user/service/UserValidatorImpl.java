package com.fitness.courses.http.user.service;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.NotFoundException;

@Service
public class UserValidatorImpl implements UserValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(UserValidatorImpl.class);

    private final UserService userService;

    @Autowired
    public UserValidatorImpl(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public void validateUserExist(Long userId) throws NotFoundException
    {
        if (userService.findById(userId).isEmpty())
        {
            final String message = "User with id %s not exist".formatted(userId.toString());
            LOG.error(message);
            throw new NotFoundException(message);
        }
    }
}
