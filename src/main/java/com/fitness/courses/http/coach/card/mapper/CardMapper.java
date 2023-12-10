package com.fitness.courses.http.coach.card.mapper;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;

import com.fitness.courses.http.coach.card.model.dto.CardInfoDto;
import com.fitness.courses.http.coach.card.model.dto.ListCardInfoDto;
import com.fitness.courses.http.coach.card.model.dto.NewCardDto;
import com.fitness.courses.http.coach.card.model.entity.CardEntity;

public class CardMapper
{
    private CardMapper()
    {
    }

    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

    static
    {
        BeanMappingBuilder newCardBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(NewCardDto.class, CardEntity.class)
                        .exclude("images")
                        .exclude("video");
            }
        };

        BeanMappingBuilder cardInfoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(CardInfoDto.class, CardEntity.class)
                        .exclude("images")
                        .exclude("video");
            }
        };

        BeanMappingBuilder listCardInfoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(ListCardInfoDto.class, CardEntity.class);
            }
        };

        MAPPER.addMapping(newCardBuilder);
        MAPPER.addMapping(cardInfoBuilder);
        MAPPER.addMapping(listCardInfoBuilder);
    }

    public static @NotNull CardEntity toEntity(@NotNull NewCardDto dto)
    {
        return MAPPER.map(dto, CardEntity.class);
    }

    public static @NotNull CardInfoDto toDto(@NotNull CardEntity entity)
    {
        CardInfoDto cardInfoDto = MAPPER.map(entity, CardInfoDto.class);
        return null;
    }

    public static @NotNull ListCardInfoDto toListCardDto(@NotNull CardEntity entity)
    {
        ListCardInfoDto listCardInfoDto = MAPPER.map(entity, ListCardInfoDto.class);
        return null;
    }
}
