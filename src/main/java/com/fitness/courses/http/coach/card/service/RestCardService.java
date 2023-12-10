package com.fitness.courses.http.coach.card.service;

import java.util.List;

import com.fitness.courses.http.coach.card.model.dto.CardInfoDto;
import com.fitness.courses.http.coach.card.model.dto.ListCardInfoDto;
import com.fitness.courses.http.coach.card.model.dto.NewCardDto;

public interface RestCardService
{
    void createCard(NewCardDto newCardDto);

    CardInfoDto getCard(Long id);

    List<ListCardInfoDto> getUserCards();
}
