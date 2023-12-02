package com.fitness.courses.http.coach.card.service;

import org.springframework.stereotype.Service;

import com.fitness.courses.http.coach.card.model.dto.NewCardDto;

@Service
public class RestCardServiceImpl implements RestCardService
{
    private final CardService cardService;

    public RestCardServiceImpl(CardService cardService)
    {
        this.cardService = cardService;
    }

    @Override
    public void createCard(NewCardDto newCardDto)
    {
        // TODO add validation
        cardService.addCard(newCardDto);
    }
}
