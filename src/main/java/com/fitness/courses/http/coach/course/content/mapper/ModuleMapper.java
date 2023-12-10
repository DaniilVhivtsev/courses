package com.fitness.courses.http.coach.course.content.mapper;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import com.fitness.courses.http.coach.course.content.model.dto.CourseAuthorModuleInfo;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;

public class ModuleMapper
{
    private ModuleMapper()
    {
    }

    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

    static
    {
        BeanMappingBuilder courseAuthorModuleInfoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(CourseAuthorModuleInfo.class, ModuleEntity.class);
            }
        };


        MAPPER.addMapping(courseAuthorModuleInfoBuilder);
    }

    public static CourseAuthorModuleInfo toCourseAuthorModuleInfo(ModuleEntity entity, List<LessonEntity> lessons)
    {
        CourseAuthorModuleInfo dto = MAPPER.map(entity, CourseAuthorModuleInfo.class);
        dto.setLessons(lessons.stream().map(LessonMapper::toCourseAuthorLessonInfo).toList());

        return dto;
    }
}
