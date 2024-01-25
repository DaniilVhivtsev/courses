package com.fitness.courses.http.coach.course.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.attachment.model.info.MultipartFileWithExtension;
import com.fitness.courses.http.attachment.service.AttachmentService;
import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.coach.course.content.model.info.LessonWithStagesInfo;
import com.fitness.courses.http.coach.course.content.service.lesson.LessonService;
import com.fitness.courses.http.coach.course.content.service.module.ModuleService;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.model.entity.CourseStatus;
import com.fitness.courses.http.coach.course.model.info.CourseEntityWithStudentsCount;
import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;
import com.fitness.courses.http.user.model.User;

@Service
public class CourseServiceImpl implements CourseService
{
    private final CrudCourseEntityService crudCourseEntityService;
    private final AuthService authService;
    private final AttachmentService attachmentService;
    private final ModuleService moduleService;
    private final LessonService lessonService;

    @Autowired
    public CourseServiceImpl(
            CrudCourseEntityService crudCourseEntityService,
            AuthService authService,
            AttachmentService attachmentService,
            ModuleService moduleService,
            LessonService lessonService)
    {
        this.crudCourseEntityService = crudCourseEntityService;
        this.authService = authService;
        this.attachmentService = attachmentService;
        this.moduleService = moduleService;
        this.lessonService = lessonService;
    }

    @Override
    public CourseEntity createCourse(@NonNull CourseEntity newCourseEntity)
    {
        newCourseEntity.setAuthor(authService.getCurrentUserOrThrow());
        newCourseEntity.setDateTimeCreated(LocalDateTime.now(ZoneId.systemDefault()));
        newCourseEntity.setStatus(CourseStatus.PUBLISHED); // TODO edit
        return crudCourseEntityService.save(newCourseEntity);
    }

    @Override
    public void delete(Long courseId)
    {
        CourseEntity courseEntityFromDb = getCourseOrThrow(courseId);

        moduleService.deleteAllByCourse(courseEntityFromDb);
        crudCourseEntityService.deleteById(courseId);
    }

    @Override
    public CourseEntity editCourseGeneralInfo(Long courseId, CourseEntity editCourseInfo, MultipartFile logo)
    {
        CourseEntity courseEntityFromDB = getCourseOrThrow(courseId);
        updateSourceInfo(courseEntityFromDB, editCourseInfo);
        if (logo != null && !logo.isEmpty())
        {
            courseEntityFromDB.setLogo(
                    attachmentService.add(
                            new MultipartFileWithExtension(
                                    FileExtensionEnum.getEnum(FilenameUtils.getExtension(logo.getOriginalFilename())),
                                    logo)
                    )
            );
        }

        return crudCourseEntityService.update(courseEntityFromDB);
    }

    private void updateSourceInfo(CourseEntity source, CourseEntity editedEntity)
    {
        for (Field field : CourseEntity.class.getDeclaredFields())
        {
            try
            {
                field.setAccessible(true);
                Object fieldValue = field.get(editedEntity);
                if (fieldValue != null)
                {
                    field.set(source, fieldValue);
                }
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            finally
            {
                field.setAccessible(false);
            }
        }
    }

    @Override
    public Optional<CourseEntity> getCourseOptional(@NotNull Long id)
    {
        return crudCourseEntityService.findById(id);
    }

    @Override
    public CourseEntity getCourseOrThrow(@NotNull Long id)
    {
        return crudCourseEntityService.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find course with id %s".formatted(id)));
    }

    @Override
    public List<CourseEntity> getAllCoursesWhereCurrentUserIsAuthor()
    {
        return crudCourseEntityService.findByAuthorId(authService.getCurrentUserOrThrow().getId());
    }

    @Override
    public List<CourseEntity> getAllCoursesWhereUserIsAuthor(@NotNull User user)
    {
        return crudCourseEntityService.findByAuthorId(user.getId());
    }

    @Override
    public List<CourseEntity> getNewCourses(@NotNull Integer offset, @NotNull Integer limit)
    {
        return crudCourseEntityService.findAllSortByTimeCreatedDesc(offset, limit);
    }

    @Override
    public List<CourseEntityWithStudentsCount> getPopularCourses(@NotNull Integer offset, @NotNull Integer limit)
    {
        return crudCourseEntityService.findAllSortByStudentsCount(offset, limit);
    }

    @Override
    public List<CourseEntity> findAllByKeyword(@NotNull String keyword, @NotNull Integer offset,
            @NotNull Integer limit)
    {
        return crudCourseEntityService.findAllByKeyword(keyword, offset, limit);
    }

    @Override
    public Set<String> getCourseStagesUuids(CourseEntity course)
    {
        return moduleService.findAllByCourseAndSortAscBySerialNumber(course).stream()
                .map(lessonService::findAllLessonsWithStagesByModule)
                .flatMap(Collection::stream)
                .map(LessonWithStagesInfo::stages)
                .flatMap(Collection::stream)
                .map(stage -> stage.getId().toString())
                .collect(Collectors.toSet());
    }
}
