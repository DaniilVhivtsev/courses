package com.fitness.courses.http.coach.course.content.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import com.fitness.courses.http.coach.course.content.model.dto.lesson.CourseAuthorLessonInfo;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;

public class LessonMapper
{
    private LessonMapper()
    {
    }

    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

    static
    {
        BeanMappingBuilder courseAuthorLessonInfoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(CourseAuthorLessonInfo.class, LessonEntity.class);
            }
        };


        MAPPER.addMapping(courseAuthorLessonInfoBuilder);
    }

    public static CourseAuthorLessonInfo toCourseAuthorLessonInfo(LessonEntity entity)
    {
        return MAPPER.map(entity, CourseAuthorLessonInfo.class);
    }
}
