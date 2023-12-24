package com.fitness.courses.http.coach.card.mapper;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import com.fitness.courses.http.attachment.model.dto.AttachmentInfoDto;
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
        cardInfoDto.setImages(
                entity.getImages().stream()
                        .map(image ->
                                new AttachmentInfoDto()
                                        .setId(image.getId())
                                        .setFileName(image.getFileName())
                                        .setUrl(image.getFileEntity().getUrl())
                        )
                        .toList()
        );
        cardInfoDto.setVideo(new AttachmentInfoDto()
                .setId(entity.getVideo().getId())
                .setFileName(entity.getVideo().getFileName())
                .setUrl(entity.getVideo().getFileEntity().getUrl()));

        return cardInfoDto;
    }

    public static @NotNull ListCardInfoDto toListCardDto(@NotNull CardEntity entity)
    {
        ListCardInfoDto dto = MAPPER.map(entity, ListCardInfoDto.class);
        dto.setMainImage(
                entity.getImages().stream()
                        .findFirst()
                        .map(image -> new AttachmentInfoDto()
                                .setId(image.getId())
                                .setFileName(image.getFileName())
                                .setUrl(image.getFileEntity().getUrl())
                        )
                        .orElse(null)
        );

        return dto;
    }
}
