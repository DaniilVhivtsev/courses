package com.fitness.courses.http.user.mapper;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

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
}
