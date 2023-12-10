package com.fitness.courses.http.coach.card.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.card.model.dto.NewCardDto;
import com.fitness.courses.http.coach.card.model.entity.CardEntity;

public interface CardService
{
    void addCard(NewCardDto newCardDto);

    Optional<CardEntity> getCardOptional(@NotNull Long id);

    CardEntity getCardOrThrow(@NotNull Long id);

    List<CardEntity> getCurrentUserCards();
}
