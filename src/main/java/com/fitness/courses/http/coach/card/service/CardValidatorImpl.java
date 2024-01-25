package com.fitness.courses.http.coach.card.service;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;
import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.coach.card.model.entity.CardEntity;
import com.fitness.courses.http.user.model.User;

@Service
public class CardValidatorImpl implements CardValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(CardValidatorImpl.class);

    private final CardService cardService;
    private final AuthService authService;

    @Autowired
    public CardValidatorImpl(
            CardService cardService,
            AuthService authService)
    {
        this.cardService = cardService;
        this.authService = authService;
    }

    @Override
    public void validateCardExist(final @NotNull Long id) throws ValidationException
    {
        if (cardService.getCardOptional(id).isEmpty())
        {
            final String message = "Card not exist";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateCurrentUserHasPermission(final @NotNull Long cardId) throws ValidationException
    {
        final User currentUser = authService.getCurrentUserOrThrow();
        final CardEntity card = cardService.getCardOrThrow(cardId);

        if (!card.getAuthor().getId().equals(currentUser.getId()))
        {
            final String message = "The user doesn't have permission to work with card";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
