package com.fitness.courses.http.coach.course.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.http.coach.course.content.model.dto.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.NewCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.UpdateCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.service.lesson.LessonService;
import com.fitness.courses.http.coach.course.content.service.lesson.LessonValidator;
import com.fitness.courses.http.coach.course.content.service.module.ModuleService;
import com.fitness.courses.http.coach.course.content.service.module.ModuleValidator;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorContentInfo;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorGeneralInfoDto;
import com.fitness.courses.http.coach.course.model.dto.EditCourseAuthorGeneralInfo;
import com.fitness.courses.http.coach.course.model.dto.ListCourseInfoDto;
import com.fitness.courses.http.coach.course.model.dto.NewCourseDto;
import com.fitness.courses.http.coach.course.mapper.CourseMapper;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.user.dto.UserGeneralInfoDto;
import com.fitness.courses.http.user.mapper.UserMapper;

@Service
public class RestCourseServiceImpl implements RestCourseService
{
    private final CourseService courseService;
    private final CourseValidator courseValidator;
    private final ModuleService moduleService;
    private final ModuleValidator moduleValidator;
    private final LessonValidator lessonValidator;
    private final LessonService lessonService;

    @Autowired
    public RestCourseServiceImpl(
            CourseService courseService,
            CourseValidator courseValidator,
            ModuleService moduleService,
            ModuleValidator moduleValidator,
            LessonValidator lessonValidator,
            LessonService lessonService)
    {
        this.courseService = courseService;
        this.courseValidator = courseValidator;
        this.moduleService = moduleService;
        this.moduleValidator = moduleValidator;
        this.lessonValidator = lessonValidator;
        this.lessonService = lessonService;
    }

    @Override
    public void createCourse(@NonNull NewCourseDto newCourseDto)
    {
        courseValidator.validateCourseTitle(newCourseDto.getTitle());

        courseService.createCourse(CourseMapper.toEntity(newCourseDto));
    }

    @Override
    public List<ListCourseInfoDto> getAuthorCourses()
    {
        return courseService.getAllCoursesWhereCurrentUserIsAuthor().stream()
                .map(CourseMapper::toListCourseInfoDto)
                .toList();
    }

    @Override
    public CourseAuthorGeneralInfoDto getAuthorCourseGeneralInfo(Long courseId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        return CourseMapper.toCourseAuthorInfoDto(courseService.getCourseOrThrow(courseId));
    }

    @Override
    public CourseAuthorGeneralInfoDto editAuthCourseGeneralInfo(Long courseId,
            EditCourseAuthorGeneralInfo editCourseAuthorGeneralInfo)
    {
        courseValidator.validateCourseTitle(editCourseAuthorGeneralInfo.getTitle());
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        MultipartFile logo = editCourseAuthorGeneralInfo.getLogo();
        CourseEntity editCourseInfo = CourseMapper.toEntity(editCourseAuthorGeneralInfo);

        return CourseMapper.toCourseAuthorInfoDto(courseService.editCourseGeneralInfo(courseId, editCourseInfo, logo));
    }

    @Override
    public List<UserGeneralInfoDto> getAuthorCourseStudents(Long courseId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        return courseService.getCourseOrThrow(courseId).getStudents().stream()
                .map(UserMapper::toUserGeneralInfoDto)
                .toList();
    }

    @Override
    public CourseAuthorContentInfo getAuthorCourseContent(@NotNull Long courseId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        return CourseMapper.toCourseAuthorContentInfo(
                moduleService.findAllModulesWithLessonsByCourse(courseService.getCourseOrThrow(courseId)));
    }

    @Override
    public void addModule(@NotNull Long courseId, @NotNull NewCourseAuthorModuleDto newModuleDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        moduleValidator.validateModuleTitle(newModuleDto.getTitle());
        moduleValidator.validateModuleDescription(newModuleDto.getDescription());

        moduleService.add(courseService.getCourseOrThrow(courseId), newModuleDto);
    }

    @Override
    public void editModule(@NotNull Long courseId, @NotNull Long moduleId,
            @NotNull UpdateCourseAuthorModuleDto updateModuleDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);
        CourseEntity courseEntityFromDb = courseService.getCourseOrThrow(courseId);

        moduleValidator.validateExist(moduleId);

        ModuleEntity moduleEntityFromDb = moduleService.getOrThrow(moduleId);
        if (updateModuleDto.getTitle() != null)
        {
            moduleValidator.validateModuleTitle(updateModuleDto.getTitle());
        }
        else
        {
            updateModuleDto.setTitle(moduleEntityFromDb.getTitle());
        }

        if (updateModuleDto.getDescription() != null)
        {
            moduleValidator.validateModuleDescription(updateModuleDto.getDescription());
        }
        else
        {
            updateModuleDto.setDescription(moduleEntityFromDb.getDescription());
        }

        if (updateModuleDto.getSerialNumber() != null)
        {
            moduleValidator.validateSerialNumber(courseEntityFromDb, updateModuleDto.getSerialNumber());
        }
        else
        {
            updateModuleDto.setSerialNumber(moduleEntityFromDb.getSerialNumber());
        }

        moduleService.update(courseEntityFromDb, moduleId, updateModuleDto);
    }

    @Override
    public void addLesson(@NotNull Long courseId, @NotNull Long moduleId,
            @NotNull NewCourseAuthorLessonDto newLessonDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        moduleValidator.validateModuleBelongsToCourse(courseId, moduleId);

        lessonValidator.validateTitle(newLessonDto.getTitle());

        lessonService.add(moduleService.getOrThrow(moduleId), newLessonDto);
    }
}
