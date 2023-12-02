package com.fitness.courses.http.coach.card.mapper;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

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

        MAPPER.addMapping(newCardBuilder);
    }

    public static @NotNull CardEntity toEntity(@NotNull NewCardDto dto)
    {
        return MAPPER.map(dto, CardEntity.class);
    }
}
