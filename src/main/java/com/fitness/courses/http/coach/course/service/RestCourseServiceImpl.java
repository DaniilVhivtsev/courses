package com.fitness.courses.http.coach.course.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fitness.courses.global.utils.UUIDGenerator;
import com.fitness.courses.http.coach.card.service.CardValidator;
import com.fitness.courses.http.coach.course.content.mapper.StageMapper;
import com.fitness.courses.http.coach.course.content.model.dto.lesson.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.lesson.UpdateCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.module.NewCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.module.UpdateCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.AddCourseAuthorStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageWithContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.UpdateCourseAuthorStageDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.StageContentType;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateAbstractStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateExercisesStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateImgStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateTextStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateVideoStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateAbstractExerciseContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseDistanceSetContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseRepeatSetContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseTimeSetContentDto;
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
import com.fitness.courses.http.student.model.entity.StudentEntity;
import com.fitness.courses.http.student.service.student.StudentService;
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
    private final ObjectMapper objectMapper;
    private final CardValidator cardValidator;
    private final StudentService studentService;

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
            StageValidator stageValidator,
            ObjectMapper objectMapper,
            CardValidator cardValidator,
            StudentService studentService)
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
        this.objectMapper = objectMapper;
        this.cardValidator = cardValidator;
        this.studentService = studentService;
    }

    @Override
    public Long createCourse(@NonNull NewCourseDto newCourseDto)
    {
        courseValidator.validateCourseTitle(newCourseDto.getTitle());

        return courseService.createCourse(CourseMapper.toEntity(newCourseDto)).getId();
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

        final CourseEntity course = courseService.getCourseOrThrow(courseId);
        return studentService.getStudents(course).stream()
                .map(StudentEntity::getUser)
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
    public Long addModule(@NotNull Long courseId, @NotNull NewCourseAuthorModuleDto newModuleDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        moduleValidator.validateModuleTitle(newModuleDto.getTitle());
        moduleValidator.validateModuleDescription(newModuleDto.getDescription());

        return moduleService.add(courseService.getCourseOrThrow(courseId), newModuleDto).getId();
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
    public Long addLesson(@NotNull Long courseId, @NotNull Long moduleId,
            @NotNull NewCourseAuthorLessonDto newLessonDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        moduleValidator.validateExist(moduleId);
        moduleValidator.validateModuleBelongsToCourse(courseId, moduleId);

        lessonValidator.validateTitle(newLessonDto.getTitle());

        return lessonService.add(moduleService.getOrThrow(moduleId), newLessonDto).getId();
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
    public Long addStage(@NotNull Long courseId, @NotNull Long lessonId, @NotNull String stageTitle)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToCourse(courseId, lessonId);

        return stageService.add(lessonService.getOrThrow(lessonId), stageTitle).getId();
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
    public List<CourseAuthorStageWithContentInfoDto> getStagesWithContent(@NotNull Long courseId,
            @NotNull Long lessonId)
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

    @Override
    public String addStageContent(@NotNull Long courseId, @NotNull Long lessonId, @NotNull Long stageId,
            @NotNull AddCourseAuthorStageContentInfoDto addContentDto)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToCourse(courseId, lessonId);

        stageValidator.validateExist(stageId);
        stageValidator.validateStageBelongsToLesson(lessonId, stageId);

        return stageService.addContent(stageId, addContentDto);
    }

    @Override
    public void editStageContent(@NotNull Long courseId, @NotNull Long lessonId, @NotNull Long stageId,
            @NotNull StageContentType type, @NotNull MultiValueMap<String, Object> formData,
            @Nullable MultipartFile multipartFile)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        lessonValidator.validateExist(lessonId);
        lessonValidator.validateLessonBelongsToCourse(courseId, lessonId);

        stageValidator.validateExist(stageId);
        stageValidator.validateStageBelongsToLesson(lessonId, stageId);
        UpdateAbstractStageContentDto updateAbstractStageContentDto = null;
        try
        {
            updateAbstractStageContentDto = getUpdateAbstractStageContentDto(stageId, type,
                    formData.toSingleValueMap(), multipartFile);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e.getLocalizedMessage());
        }

        stageService.updateStageContent(stageId, updateAbstractStageContentDto);
    }

    private UpdateAbstractStageContentDto getUpdateAbstractStageContentDto(@NotNull Long stageId,
            @NotNull StageContentType type, @NotNull Map<String, Object> formData,
            @Nullable MultipartFile multipartFile) throws JsonProcessingException
    {
        UpdateAbstractStageContentDto dto = switch (type)
                {
                    case IMG -> getUpdateImgStageContentDto(stageId, formData, multipartFile);
                    case VIDEO -> getUpdateVideoStageContentDto(stageId, formData, multipartFile);
                    case TEXT -> getUpdateTextStageContentDto(stageId, formData);
                    case EXERCISES -> getUpdateExercisesStageContentDto(stageId, formData);
                };

        return dto;
    }

    private UpdateTextStageContentDto getUpdateTextStageContentDto(@NotNull Long stageId,
            @NotNull Map<String, Object> formData)
    {
        StageEntity stageEntityFromDb = stageService.getOrThrow(stageId);
        UpdateTextStageContentDto dto = objectMapper.convertValue(formData, UpdateTextStageContentDto.class);
        stageValidator.validateStageContentExist(stageId, dto.getUuid());

        if (dto.getSerialNumber() != null)
        {
            stageValidator.validateContentSerialNumber(stageId, dto.getSerialNumber());
        }
        else
        {
            dto.setSerialNumber(stageEntityFromDb.getSerialNumber());
        }

        if (dto.getTextContent() == null)
        {
            dto.setTextContent("");
        }

        return dto;
    }

    private UpdateImgStageContentDto getUpdateImgStageContentDto(@NotNull Long stageId,
            @NotNull Map<String, Object> formData, @Nullable MultipartFile multipartFile)
    {
        StageEntity stageEntityFromDb = stageService.getOrThrow(stageId);
        UpdateImgStageContentDto dto = objectMapper.convertValue(formData, UpdateImgStageContentDto.class);
        stageValidator.validateStageContentExist(stageId, dto.getUuid());

        if (dto.getSerialNumber() != null)
        {
            stageValidator.validateContentSerialNumber(stageId, dto.getSerialNumber());
        }
        else
        {
            dto.setSerialNumber(stageEntityFromDb.getSerialNumber());
        }

        dto.setImage(multipartFile);

        return dto;
    }

    private UpdateVideoStageContentDto getUpdateVideoStageContentDto(@NotNull Long stageId,
            @NotNull Map<String, Object> formData, @Nullable MultipartFile multipartFile)
    {
        StageEntity stageEntityFromDb = stageService.getOrThrow(stageId);
        UpdateVideoStageContentDto dto = objectMapper.convertValue(formData, UpdateVideoStageContentDto.class);
        stageValidator.validateStageContentExist(stageId, dto.getUuid());

        if (dto.getSerialNumber() != null)
        {
            stageValidator.validateContentSerialNumber(stageId, dto.getSerialNumber());
        }
        else
        {
            dto.setSerialNumber(stageEntityFromDb.getSerialNumber());
        }

        dto.setVideo(multipartFile);

        return dto;
    }

    private UpdateExercisesStageContentDto getUpdateExercisesStageContentDto(@NotNull Long stageId,
            @NotNull Map<String, Object> formData) throws JsonProcessingException
    {
        String exerciseListJson;

        if (formData.get("exercises") instanceof List)
        {
            List<Map<String, Object>> exercisesList = (ArrayList<Map<String, Object>>) formData.get("exercises");
            exerciseListJson = objectMapper.writeValueAsString(exercisesList);
        }
        else
        {
            exerciseListJson = (String) formData.get("exercises");
        }


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Чтение объектов из строковых представлений в списке
        List<UpdateAbstractExerciseContentDto<?>> updateAbstractExerciseContentDtoList = objectMapper.readValue(
                exerciseListJson,
                new TypeReference<List<UpdateAbstractExerciseContentDto<?>>>() {}
        );

        /*List<UpdateAbstractExerciseContentDto<?>> updateAbstractExerciseContentDtoList =
                objectMapper.readValue((String)formData.get("exercises"),
                        new TypeReference<List<UpdateAbstractExerciseContentDto<?>>>() {});*/
        formData.remove("exercises");

        StageEntity stageEntityFromDb = stageService.getOrThrow(stageId);
        UpdateExercisesStageContentDto dto = objectMapper.convertValue(formData, UpdateExercisesStageContentDto.class);
        dto.setExercises(updateAbstractExerciseContentDtoList);
        stageValidator.validateStageContentExist(stageId, dto.getUuid());

        if (dto.getSerialNumber() != null)
        {
            stageValidator.validateContentSerialNumber(stageId, dto.getSerialNumber());
        }
        else
        {
            dto.setSerialNumber(stageEntityFromDb.getSerialNumber());
        }

        if (dto.getExercises() != null)
        {
            dto.getExercises().forEach(exercise ->
            {
                if (exercise.getUuid() != null)
                {
                    stageValidator.validateExerciseContentExist(stageId, dto.getUuid(), exercise.getUuid());
                }
                else
                {
                    exercise.setUuid(UUIDGenerator.nestUuidInString());
                }

                cardValidator.validateCardExist(exercise.getCardId());

                if (exercise.getSets() != null)
                {
                    exercise.getSets().forEach(set ->
                    {
                        if (set.getUuid() != null)
                        {
                            stageValidator.validateExerciseSetContentExist(stageId, dto.getUuid(), exercise.getUuid(),
                                    set.getUuid());
                        }
                        else
                        {
                            set.setUuid(UUIDGenerator.nestUuidInString());
                        }

                        if (set.getCountOfKilograms() == null || StringUtils.isBlank(set.getCountOfKilograms()))
                        {
                            set.setCountOfKilograms("0");
                        }
                        else
                        {
                            // TODO добавить проверку формулы или числа
                        }

                        if (set.getPauseAfter() == null)
                        {
                            set.setPauseAfter(LocalTime.of(0, 0, 0));
                        }

                        if (set instanceof UpdateExerciseDistanceSetContentDto setDistance)
                        {
                            if (setDistance.getDistanceKilometers() == null || StringUtils.isBlank(setDistance.getDistanceKilometers()))
                            {
                                setDistance.setDistanceKilometers("0");
                            }
                            else
                            {
                                // TODO добавить проверку формулы или числа
                            }
                        }
                        else if (set instanceof UpdateExerciseRepeatSetContentDto setRepeat)
                        {
                            if (setRepeat.getRepeatCount() == null || StringUtils.isBlank(setRepeat.getRepeatCount()))
                            {
                                setRepeat.setRepeatCount("0");
                            }
                            else
                            {
                                // TODO добавить проверку формулы или числа
                            }
                        }
                        else if (set instanceof UpdateExerciseTimeSetContentDto setTime)
                        {
                            if (setTime.getExecutionTime() == null || StringUtils.isBlank(setTime.getExecutionTime()))
                            {
                                setTime.setExecutionTime("0");
                            }
                            else
                            {
                                // TODO добавить проверку формулы или числа
                            }
                        }

                    });
                }
                else
                {
                    exercise.setSets(new ArrayList<>());
                }
            });
        }
        else
        {
            dto.setExercises(new ArrayList<>());
        }

        return dto;
    }
}
