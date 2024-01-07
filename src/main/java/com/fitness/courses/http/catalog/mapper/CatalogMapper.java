package com.fitness.courses.http.catalog.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.catalog.model.dto.CatalogBySearchValueCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogNewCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CatalogPopularCourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.CourseInfoDto;
import com.fitness.courses.http.catalog.model.dto.content.LessonInfoDto;
import com.fitness.courses.http.catalog.model.dto.content.ModuleInfoDto;
import com.fitness.courses.http.catalog.model.dto.content.StageInfoDto;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;
import com.fitness.courses.http.coach.course.content.service.module.ModuleService;
import com.fitness.courses.http.coach.course.content.service.stage.StageService;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.model.info.CourseEntityWithStudentsCount;
import com.fitness.courses.http.student.service.student.StudentService;
import com.fitness.courses.http.user.model.User;

@Service
public class CatalogMapper
{
    private final AuthService authService;
    private final StudentService studentService;
    private final ModuleService moduleService;
    private final StageService stageService;

    @Autowired
    public CatalogMapper(AuthService authService,
            StudentService studentService,
            ModuleService moduleService,
            StageService stageService)
    {
        this.authService = authService;
        this.studentService = studentService;
        this.moduleService = moduleService;
        this.stageService = stageService;
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

        BeanMappingBuilder courseInfoDtoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(CourseInfoDto.class, CourseEntity.class)
                        .fields("description", "shortDescription"); // TODO check categories, dateTimeCreated
            }
        };

        BeanMappingBuilder stageInfoDtoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(StageInfoDto.class, StageEntity.class);
            }
        };

        MAPPER.addMapping(catalogNewCourseInfoDtoBuilder);
        MAPPER.addMapping(catalogPopularCourseInfoDtoBuilder);
        MAPPER.addMapping(catalogBySearchValueCourseInfoDtoBuilder);
        MAPPER.addMapping(courseInfoDtoBuilder);
        MAPPER.addMapping(stageInfoDtoBuilder);
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

    public CourseInfoDto toCourseInfoDto(@NotNull CourseEntity entity)
    {
        CourseInfoDto dto = MAPPER.map(entity, CourseInfoDto.class);
        dto.setAuthorId(entity.getAuthor().getId());
        dto.setAuthorFullName(entity.getAuthor().getFullName());
        dto.setAuthorShortDescription("Example description. TODO edit");

        dto.setIconImgUrl(
                entity.getLogo()
                        .getFileEntity()
                        .getUrl()
        );
        dto.setRating(0.0);
        dto.setNumberOfPeople(studentService.countByCourse(entity));
        dto.setFree(true);
        dto.setPrice(0);

        Optional<User> currentUserOptional = authService.getCurrentUser();
        currentUserOptional.ifPresentOrElse(
                user -> dto.setUserIsRegisteredForTheCourse(studentService.studentWithUserAndCourseExist(user, entity)),
                () -> dto.setUserIsRegisteredForTheCourse(false));

        Set<String> doneStageAndSetUuids = new HashSet<>();
        currentUserOptional.flatMap(user -> studentService.getByUserAndCourse(user, entity))
                .ifPresent(student -> doneStageAndSetUuids.addAll(student.getDoneStageAndSetUuids()));

        List<ModuleInfoDto> modules = moduleService.findAllModulesWithLessonsByCourse(entity).stream()
                .map(moduleWithLessonsInfo ->
                {
                    ModuleEntity module = moduleWithLessonsInfo.module();
                    List<LessonEntity> lessons = moduleWithLessonsInfo.lessons();

                    ModuleInfoDto moduleInfoDto = new ModuleInfoDto();
                    moduleInfoDto.setId(module.getId());
                    moduleInfoDto.setSerialNumber(module.getSerialNumber());
                    moduleInfoDto.setTitle(module.getTitle());
                    moduleInfoDto.setLessons(
                            lessons.stream().map(lesson ->
                                    {
                                        List<StageEntity> stages =
                                                stageService.findAllByLessonAndSortAscBySerialNumber(lesson);
                                        Set<String> stagesUuids = stages.stream()
                                                .map(stage -> stage.getId().toString())
                                                .collect(Collectors.toSet());
                                        // удаляем идентификаторы пройденных этапов
                                        stagesUuids.removeAll(doneStageAndSetUuids);

                                        LessonInfoDto lessonInfoDto = new LessonInfoDto();
                                        lessonInfoDto.setId(lesson.getId());
                                        lessonInfoDto.setTitle(lesson.getTitle());
                                        lessonInfoDto.setSerialNumber(lesson.getSerialNumber());
                                        lessonInfoDto.setStagesNumber(stages.size());
                                        lessonInfoDto.setCompletedStagesCount(stages.size() - stagesUuids.size());

                                        return lessonInfoDto;
                                    })
                                    .toList());

                    return moduleInfoDto;
                })
                .toList();

        dto.setModules(modules);

        return dto;
    }

    public StageInfoDto toStageInfoDto(@NotNull StageEntity stage, @NotNull Set<String> doneStageAndSetUuids)
    {
        StageInfoDto dto = MAPPER.map(stage, StageInfoDto.class);
        dto.setCompleted(doneStageAndSetUuids.contains(stage.getId().toString()));
        dto.setTitle("Example title");

        return dto;
    }
}
