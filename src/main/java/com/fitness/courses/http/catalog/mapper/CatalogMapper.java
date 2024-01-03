package com.fitness.courses.http.catalog.mapper;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.catalog.model.dto.CatalogBySearchValueCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogNewCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogPopularCourseInfoDto;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.model.info.CourseEntityWithStudentsCount;
import com.fitness.courses.http.student.service.student.StudentService;
import com.fitness.courses.http.user.model.User;

@Service
public class CatalogMapper
{
    private final AuthService authService;
    private final StudentService studentService;

    @Autowired
    public CatalogMapper(
            AuthService authService,
            StudentService studentService)
    {
        this.authService = authService;
        this.studentService = studentService;
    }

    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

    static
    {
        BeanMappingBuilder catalogNewCourseInfoDtoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(CatalogNewCourseInfoDto.class, CourseEntity.class)
                        .fields("description", "shortDescription");
            }
        };

        BeanMappingBuilder catalogPopularCourseInfoDtoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(CatalogPopularCourseInfoDto.class, CourseEntity.class)
                        .fields("description", "shortDescription");
            }
        };

        BeanMappingBuilder catalogBySearchValueCourseInfoDtoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(CatalogBySearchValueCourseInfoDto.class, CourseEntity.class)
                        .fields("description", "shortDescription");
            }
        };

        MAPPER.addMapping(catalogNewCourseInfoDtoBuilder);
        MAPPER.addMapping(catalogPopularCourseInfoDtoBuilder);
        MAPPER.addMapping(catalogBySearchValueCourseInfoDtoBuilder);
    }

    public CatalogNewCourseInfoDto toCatalogNewCourseInfoDto(@NotNull CourseEntity entity)
    {
        CatalogNewCourseInfoDto dto = MAPPER.map(entity, CatalogNewCourseInfoDto.class);
        dto.setIconImgUrl(
                entity.getLogo()
                        .getFileEntity()
                        .getUrl()
        );
        dto.setRating(0.0);
        dto.setNumberOfPeople(studentService.countByCourse(entity));
        dto.setFree(true);
        dto.setPrice(0);
        dto.setAuthorId(entity.getAuthor().getId());
        dto.setAuthorFullName(entity.getAuthor().getFullName());

        Optional<User> currentUserOptional = authService.getCurrentUser();
        currentUserOptional.ifPresentOrElse(
                user -> dto.setUserIsRegisteredForTheCourse(studentService.studentWithUserAndCourseExist(user, entity)),
                () -> dto.setUserIsRegisteredForTheCourse(false));

        return dto;
    }

    public CatalogPopularCourseInfoDto toCatalogPopularCourseInfoDto(
            @NotNull CourseEntityWithStudentsCount courseEntityWithStudentsCount)
    {
        final CourseEntity entity = courseEntityWithStudentsCount.course();
        final Integer studentsCount = courseEntityWithStudentsCount.studentsCount();
        CatalogPopularCourseInfoDto dto = MAPPER.map(entity, CatalogPopularCourseInfoDto.class);
        dto.setIconImgUrl(
                entity.getLogo()
                        .getFileEntity()
                        .getUrl()
        );
        dto.setRating(0.0);
        dto.setNumberOfPeople(studentsCount);
        dto.setFree(true);
        dto.setPrice(0);
        dto.setAuthorId(entity.getAuthor().getId());
        dto.setAuthorFullName(entity.getAuthor().getFullName());

        Optional<User> currentUserOptional = authService.getCurrentUser();
        currentUserOptional.ifPresentOrElse(
                user -> dto.setUserIsRegisteredForTheCourse(studentService.studentWithUserAndCourseExist(user, entity)),
                () -> dto.setUserIsRegisteredForTheCourse(false));

        return dto;
    }

    public CatalogBySearchValueCourseInfoDto toCatalogBySearchValueCourseInfoDto(@NotNull CourseEntity entity)
    {
        CatalogBySearchValueCourseInfoDto dto = MAPPER.map(entity, CatalogBySearchValueCourseInfoDto.class);
        dto.setIconImgUrl(
                entity.getLogo()
                        .getFileEntity()
                        .getUrl()
        );
        dto.setRating(0.0);
        dto.setNumberOfPeople(studentService.countByCourse(entity));
        dto.setFree(true);
        dto.setPrice(0);
        dto.setAuthorId(entity.getAuthor().getId());
        dto.setAuthorFullName(entity.getAuthor().getFullName());

        Optional<User> currentUserOptional = authService.getCurrentUser();
        currentUserOptional.ifPresentOrElse(
                user -> dto.setUserIsRegisteredForTheCourse(studentService.studentWithUserAndCourseExist(user, entity)),
                () -> dto.setUserIsRegisteredForTheCourse(false));

        return dto;
    }
}
