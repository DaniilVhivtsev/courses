package com.fitness.courses.http.coach.course.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.lang.NonNull;

import com.fitness.courses.http.coach.course.dto.NewCourseDto;
import com.fitness.courses.http.coach.course.model.CourseEntity;

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

        MAPPER.addMapping(newCourseBuilder);
    }

    public static @NonNull CourseEntity toEntity(@NonNull NewCourseDto dto)
    {
        return MAPPER.map(dto, CourseEntity.class);
    }
}
