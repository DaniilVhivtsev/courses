package com.fitness.courses.http.coach.course.mapper;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.lang.NonNull;

import com.fitness.courses.http.coach.course.model.dto.CourseAuthorContentInfo;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorGeneralInfoDto;
import com.fitness.courses.http.coach.course.model.dto.EditCourseAuthorGeneralInfo;
import com.fitness.courses.http.coach.course.model.dto.ListCourseInfoDto;
import com.fitness.courses.http.coach.course.model.dto.NewCourseDto;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.user.mapper.UserMapper;

public class CourseMapper
{
    private CourseMapper()
    {
    }

    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

    static
    {
        BeanMappingBuilder newCourseBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(NewCourseDto.class, CourseEntity.class);
            }
        };

        BeanMappingBuilder listCourseInfoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(ListCourseInfoDto.class, CourseEntity.class)
                        .exclude("logo");
            }
        };

        BeanMappingBuilder courseAuthorInfoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(CourseAuthorGeneralInfoDto.class, CourseEntity.class)
                        .exclude("author")
                        .exclude("logo");
            }
        };

        BeanMappingBuilder editedCourseAuthorInfoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(EditCourseAuthorGeneralInfo.class, CourseEntity.class)
                        .exclude("logo");
            }
        };

        MAPPER.addMapping(newCourseBuilder);
        MAPPER.addMapping(listCourseInfoBuilder);
        MAPPER.addMapping(courseAuthorInfoBuilder);
        MAPPER.addMapping(editedCourseAuthorInfoBuilder);
    }

    public static @NotNull CourseEntity toEntity(@NotNull NewCourseDto dto)
    {
        // TODO logo
        return MAPPER.map(dto, CourseEntity.class);
    }

    public static @NotNull CourseEntity toEntity(@NotNull EditCourseAuthorGeneralInfo dto)
    {
        return MAPPER.map(dto, CourseEntity.class);
    }

    public static @NotNull ListCourseInfoDto toListCourseInfoDto(@NotNull CourseEntity entity)
    {
        // TODO logo
        return MAPPER.map(entity, ListCourseInfoDto.class);
    }

    public static @NotNull CourseAuthorGeneralInfoDto toCourseAuthorInfoDto(@NotNull CourseEntity entity)
    {
        CourseAuthorGeneralInfoDto dto = MAPPER.map(entity, CourseAuthorGeneralInfoDto.class);
        dto.setAuthor(UserMapper.toUserGeneralInfoDto(entity.getAuthor()));
        dto.setLogo(null);
        return dto;
    }

    public static @NotNull CourseAuthorContentInfo ()
}
