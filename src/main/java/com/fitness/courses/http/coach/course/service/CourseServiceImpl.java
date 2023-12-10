package com.fitness.courses.http.coach.course.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

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
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;

@Service
public class CourseServiceImpl implements CourseService
{
    private final CrudCourseEntityService crudCourseEntityService;
    private final AuthService authService;
    private final AttachmentService attachmentService;

    @Autowired
    public CourseServiceImpl(
            CrudCourseEntityService crudCourseEntityService,
            AuthService authService,
            AttachmentService attachmentService)
    {
        this.crudCourseEntityService = crudCourseEntityService;
        this.authService = authService;
        this.attachmentService = attachmentService;
    }

    @Override
    public void createCourse(@NonNull CourseEntity newCourseEntity)
    {
        newCourseEntity.setAuthor(authService.getCurrentUserOrThrow());
        newCourseEntity.setDateTimeCreated(LocalDateTime.now(ZoneId.systemDefault()));
        crudCourseEntityService.save(newCourseEntity);
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
        for (Field field : CourseEntity.class.getFields())
        {
            try
            {
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
}
