package com.fitness.courses.http.coach.course.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.http.coach.course.content.mapper.StageMapper;
import com.fitness.courses.http.coach.course.content.model.dto.lesson.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.module.NewCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.lesson.UpdateCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.module.UpdateCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageWithContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.UpdateCourseAuthorStageDto;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;
import com.fitness.courses.http.coach.course.content.service.lesson.LessonService;
import com.fitness.courses.http.coach.course.content.service.lesson.LessonValidator;
import com.fitness.courses.http.coach.course.content.service.module.ModuleService;
import com.fitness.courses.http.coach.course.content.service.module.ModuleValidator;
import com.fitness.courses.http.coach.course.content.service.stage.StageService;
import com.fitness.courses.http.coach.course.content.service.stage.StageValidator;
import com.fitness.courses.http.coach.course.mapper.CourseMapper;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorContentInfo;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorGeneralInfoDto;
import com.fitness.courses.http.coach.course.model.dto.EditCourseAuthorGeneralInfo;
import com.fitness.courses.http.coach.course.model.dto.ListCourseInfoDto;
import com.fitness.courses.http.coach.course.model.dto.NewCourseDto;
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
    private final StageService stageService;
    private final StageMapper stageMapper;
    private final StageValidator stageValidator;

    @Autowired
    public RestCourseServiceImpl(
            CourseService courseService,
            CourseValidator courseValidator,
            ModuleService moduleService,
            ModuleValidator moduleValidator,
            LessonValidator lessonValidator,
            LessonService lessonService,
            StageService stageService,
            StageMapper stageMapper,
            StageValidator stageValidator)
    {
        this.courseService = courseService;
        this.courseValidator = courseValidator;
        this.moduleService = moduleService;
        this.moduleValidator = moduleValidator;
        this.lessonValidator = lessonValidator;
        this.lessonService = lessonService;
        this.stageService = stageService;
        this.stageMapper = stageMapper;
        this.stageValidator = stageValidator;
    }

    @Override
    public void createCourse(@NonNull NewCourseDto newCourseDto)
    {
        courseValidator.validateCourseTitle(newCourseDto.getTitle());

        courseService.createCourse(CourseMapper.toEntity(newCourseDto));
    }

    @Override
    public void deleteCourse(Long courseId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        courseService.delete(courseId);
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
        moduleValidator.validateModuleBelongsToCourse(courseId, moduleId);

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
    public void deleteModule(@NotNull Long courseId, @NotNull Long moduleId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);
        CourseEntity courseEntityFromDb = courseService.getCourseOrThrow(courseId);

        moduleValidator.validateExist(moduleId);
        moduleValidator.validateModuleBelongsToCourse(courseId, moduleId);
        moduleValidator.validateNoLessonsInModule(moduleId);

        moduleService.delete(courseEntityFromDb, moduleId);
    }

    @Override
    public void addLesson(@NotNull Long courseId, @NotNull Long moduleId,
            @NotNull NewCourseAuthorLessonDto newLessonDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        moduleValidator.validateExist(moduleId);
        moduleValidator.validateModuleBelongsToCourse(courseId, moduleId);

        lessonValidator.validateTitle(newLessonDto.getTitle());

        lessonService.add(moduleService.getOrThrow(moduleId), newLessonDto);
    }

    @Override
    public void editLesson(@NotNull Long courseId, @NotNull Long moduleId, @NotNull Long lessonId,
            @NotNull UpdateCourseAuthorLessonDto updateLessonDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        moduleValidator.validateExist(moduleId);
        moduleValidator.validateModuleBelongsToCourse(courseId, moduleId);
        ModuleEntity moduleEntityFromDb = moduleService.getOrThrow(moduleId);

        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToModule(moduleId, lessonId);
        LessonEntity lessonEntityFromDb = lessonService.getOrThrow(moduleId);


        if (updateLessonDto.getTitle() != null)
        {
            lessonValidator.validateTitle(updateLessonDto.getTitle());
        }
        else
        {
            updateLessonDto.setTitle(lessonEntityFromDb.getTitle());
        }

        if (updateLessonDto.getSerialNumber() != null)
        {
            lessonValidator.validateSerialNumber(moduleEntityFromDb, updateLessonDto.getSerialNumber());
        }
        else
        {
            updateLessonDto.setSerialNumber(lessonEntityFromDb.getSerialNumber());
        }

        lessonService.update(moduleEntityFromDb, lessonId, updateLessonDto);
    }

    @Override
    public void deleteLesson(@NotNull Long courseId, @NotNull Long moduleId, @NotNull Long lessonId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        moduleValidator.validateExist(moduleId);
        moduleValidator.validateModuleBelongsToCourse(courseId, moduleId);
        ModuleEntity moduleEntityFromDb = moduleService.getOrThrow(moduleId);

        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToModule(moduleId, lessonId);
        lessonValidator.validateNoStagesInLesson(lessonId);

        lessonService.delete(moduleEntityFromDb, lessonId);
    }

    @Override
    public void addStage(@NotNull Long courseId, @NotNull Long lessonId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToCourse(courseId, lessonId);

        stageService.add(lessonService.getOrThrow(lessonId));
    }

    @Override
    public List<CourseAuthorStageInfoDto> getStages(@NotNull Long courseId, @NotNull Long lessonId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToCourse(courseId, lessonId);

        return stageService.findAllByLessonAndSortAscBySerialNumber(lessonService.getOrThrow(lessonId)).stream()
                .map(stageMapper::toInfoDto)
                .toList();
    }

    @Override
    public List<CourseAuthorStageWithContentInfoDto> getStagesWithContent(@NotNull Long courseId, @NotNull Long lessonId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToCourse(courseId, lessonId);

        return stageService.findAllByLessonAndSortAscBySerialNumber(lessonService.getOrThrow(lessonId)).stream()
                .map(stageMapper::toInfoDtoWithContent)
                .toList();
    }

    @Override
    public CourseAuthorStageWithContentInfoDto getStage(@NotNull Long courseId, @NotNull Long lessonId,
            @NotNull Long stageId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToCourse(courseId, lessonId);

        stageValidator.validateExist(stageId);
        stageValidator.validateStageBelongsToLesson(lessonId, stageId);

        return stageMapper.toInfoDtoWithContent(stageService.getOrThrow(stageId));
    }

    @Override
    public void editStage(@NotNull Long courseId, @NotNull Long lessonId, @NotNull Long stageId,
            @NotNull UpdateCourseAuthorStageDto updateStageDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToCourse(courseId, lessonId);
        final LessonEntity lessonEntityFromDb = lessonService.getOrThrow(lessonId);

        stageValidator.validateExist(stageId);
        stageValidator.validateStageBelongsToLesson(lessonId, stageId);

        final StageEntity stageEntityFromDb = stageService.getOrThrow(stageId);

        if (updateStageDto.getSerialNumber() != null)
        {
            stageValidator.validateSerialNumber(lessonEntityFromDb, updateStageDto.getSerialNumber());
        }
        else
        {
            updateStageDto.setSerialNumber(stageEntityFromDb.getSerialNumber());
        }

        stageService.update(lessonEntityFromDb, stageId, updateStageDto);
    }

    @Override
    public void deleteStage(@NotNull Long courseId, @NotNull Long lessonId, @NotNull Long stageId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToCourse(courseId, lessonId);
        final LessonEntity lessonEntityFromDb = lessonService.getOrThrow(lessonId);

        stageValidator.validateExist(stageId);
        stageValidator.validateStageBelongsToLesson(lessonId, stageId);

        stageService.delete(lessonEntityFromDb, stageId);
    }
}
