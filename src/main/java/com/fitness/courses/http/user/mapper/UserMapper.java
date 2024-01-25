package com.fitness.courses.http.user.mapper;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import com.fitness.courses.global.Languages;
import com.fitness.courses.http.user.dto.AttachmentDto;
import com.fitness.courses.http.user.dto.GeneralInfo;
import com.fitness.courses.http.user.dto.UserGeneralInfoDto;
import com.fitness.courses.http.user.model.User;

public class UserMapper
{
    private UserMapper()
    {
    }

    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

    static
    {
        BeanMappingBuilder userGeneralInfoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(UserGeneralInfoDto.class, User.class);
            }
        };

        MAPPER.addMapping(userGeneralInfoBuilder);
    }

    public static @NotNull UserGeneralInfoDto toUserGeneralInfoDto(@NotNull User entity)
    {
        return MAPPER.map(entity, UserGeneralInfoDto.class);
    }

    public static @NotNull GeneralInfo toGeneralInfo(@NotNull User entity)
    {
        GeneralInfo dto = new GeneralInfo();
        dto.setId(entity.getId());
        dto.setName(entity.getFirstName());
        dto.setSurname(entity.getLastName());
        dto.setCurrentLanguage(Languages.RU_RU);
        dto.setBiography(entity.getBiography());
        dto.setAbout(entity.getAbout());

        if (entity.getLogo() != null)
        {
            dto.setIconImgDto(new AttachmentDto()
                    .setId(entity.getLogo().getId())
                    .setLink(entity.getLogo().getFileEntity().getUrl())
                    .setTitle(entity.getLogo().getFileName()));
        }
        dto.setEmail(entity.getEmail());

        return dto;
    }
}
