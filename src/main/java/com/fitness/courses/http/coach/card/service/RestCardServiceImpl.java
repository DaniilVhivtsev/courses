package com.fitness.courses.http.coach.card.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.courses.http.coach.card.mapper.CardMapper;
import com.fitness.courses.http.coach.card.model.dto.CardInfoDto;
import com.fitness.courses.http.coach.card.model.dto.ListCardInfoDto;
import com.fitness.courses.http.coach.card.model.dto.NewCardDto;

@Service
public class RestCardServiceImpl implements RestCardService
{
    private final CardService cardService;
    private final CardValidator cardValidator;

    public RestCardServiceImpl(
            CardService cardService,
            CardValidator cardValidator)
    {
        this.cardService = cardService;
        this.cardValidator = cardValidator;
    }

    @Override
    public Long createCard(NewCardDto newCardDto)
    {
        // TODO add validation
        return cardService.addCard(newCardDto).getId();
    }

    @Override
    public CardInfoDto getCard(Long id)
    {
        cardValidator.validateCardExist(id);
        cardValidator.validateCurrentUserHasPermission(id);

        return CardMapper.toDto(cardService.getCardOrThrow(id));
    }

    @Override
    public List<ListCardInfoDto> getUserCards()
    {
        return cardService.getCurrentUserCards().stream()
                .map(CardMapper::toListCardDto)
                .toList();
    }
}
