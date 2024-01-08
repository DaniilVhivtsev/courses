package com.fitness.courses.http.coach.course.mapper;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import com.fitness.courses.global.dozer.LocalDateTimeToStringConverter;
import com.fitness.courses.http.attachment.model.dto.AttachmentInfoDto;
import com.fitness.courses.http.coach.course.content.mapper.ModuleMapper;
import com.fitness.courses.http.coach.course.content.model.info.ModuleWithLessonsInfo;
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
        MAPPER.setCustomConverters(List.of(new LocalDateTimeToStringConverter()));

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
//                        .fields("dateTimeCreated", "dateTimeCreated", FieldsMappingOptions.customConverter(LocalDateTimeToStringConverter.class))
                        .exclude("dateTimeCreated")
                        .exclude("logo");
            }
        };

        BeanMappingBuilder courseAuthorInfoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(CourseAuthorGeneralInfoDto.class, CourseEntity.class)
                        .exclude("categories")
                        .exclude("dateTimeCreated")
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
                        .exclude("categories")
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
        return MAPPER.map(dto, CourseEntity.class);
    }

    public static @NotNull CourseEntity toEntity(@NotNull EditCourseAuthorGeneralInfo dto)
    {
        return MAPPER.map(dto, CourseEntity.class);
    }

    public static @NotNull ListCourseInfoDto toListCourseInfoDto(@NotNull CourseEntity entity)
    {
        ListCourseInfoDto dto = MAPPER.map(entity, ListCourseInfoDto.class);
        dto.setDateTimeCreated(entity.getDateTimeCreated());
        dto.setLogo(new AttachmentInfoDto()
                .setId(entity.getLogo().getId())
                .setFileName(entity.getLogo().getFileName())
                .setUrl(entity.getLogo().getFileEntity().getUrl()));

        return dto;
    }

    public static @NotNull CourseAuthorGeneralInfoDto toCourseAuthorInfoDto(@NotNull CourseEntity entity)
    {
        CourseAuthorGeneralInfoDto dto = MAPPER.map(entity, CourseAuthorGeneralInfoDto.class);
        dto.setAuthor(UserMapper.toUserGeneralInfoDto(entity.getAuthor()));
        dto.setDateTimeCreated(entity.getDateTimeCreated());
        dto.setLogo(new AttachmentInfoDto()
                .setId(entity.getLogo().getId())
                .setFileName(entity.getLogo().getFileName())
                .setUrl(entity.getLogo().getFileEntity().getUrl()));

        return dto;
    }

    public static @NotNull CourseAuthorContentInfo toCourseAuthorContentInfo(
            List<ModuleWithLessonsInfo> moduleWithLessonsInfos)
    {
        CourseAuthorContentInfo courseAuthorContentInfo = new CourseAuthorContentInfo();
        courseAuthorContentInfo.setModules(moduleWithLessonsInfos.stream()
                .map(moduleWithLessonsInfo -> ModuleMapper.toCourseAuthorModuleInfo(moduleWithLessonsInfo.module(),
                        moduleWithLessonsInfo.lessons())).toList());

        return courseAuthorContentInfo;
    }
}
