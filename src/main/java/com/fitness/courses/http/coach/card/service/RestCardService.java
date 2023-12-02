package com.fitness.courses.http.coach.card.service;

import com.fitness.courses.http.coach.card.model.dto.NewCardDto;

public interface RestCardService
{
    void createCard(NewCardDto newCardDto);
}
