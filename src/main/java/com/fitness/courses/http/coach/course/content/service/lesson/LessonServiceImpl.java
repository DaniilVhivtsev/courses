package com.fitness.courses.http.coach.course.content.service.lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.attachment.model.info.MultipartFileWithExtension;
import com.fitness.courses.http.attachment.service.AttachmentService;
import com.fitness.courses.http.coach.course.content.model.dto.lesson.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.lesson.UpdateCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.model.info.LessonWithStagesInfo;
import com.fitness.courses.http.coach.course.content.service.stage.StageService;
import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;

@Service
public class LessonServiceImpl implements LessonService
{
    private final CrudLessonEntityService crudLessonEntityService;
    private final StageService stageService;
    private final AttachmentService attachmentService;

    @Autowired
    public LessonServiceImpl(
            CrudLessonEntityService crudLessonEntityService,
            StageService stageService,
            AttachmentService attachmentService)
    {
        this.crudLessonEntityService = crudLessonEntityService;
        this.stageService = stageService;
        this.attachmentService = attachmentService;
    }

    @Override
    public LessonEntity add(@NotNull ModuleEntity moduleEntity, @NotNull NewCourseAuthorLessonDto newLessonDto)
    {
        LessonEntity newLessonEntity = new LessonEntity();
        newLessonEntity.setTitle(newLessonDto.getTitle());
        newLessonEntity.setSerialNumber(getNextSerialNumber(moduleEntity));
        newLessonEntity.setModule(moduleEntity);

        return crudLessonEntityService.save(newLessonEntity);
    }

    @Override
    public void addIcon(Long lessonId, @NotNull MultipartFile icon)
    {
        LessonEntity lessonEntity = crudLessonEntityService.findById(lessonId).orElseThrow();
        if (icon != null && !icon.isEmpty())
        {
            lessonEntity.setIcon(
                    attachmentService.add(
                            new MultipartFileWithExtension(
                                    FileExtensionEnum.getEnum(FilenameUtils.getExtension(icon.getOriginalFilename())),
                                    icon)
                    )
            );
        }

        crudLessonEntityService.save(lessonEntity);
    }

    @Override
    public void deleteAllByModule(ModuleEntity moduleEntity)
    {
        findAllByModuleAndSortAscBySerialNumber(moduleEntity)
                .forEach(lesson ->
                {
                    stageService.deleteAllByLesson(lesson);
                    crudLessonEntityService.deleteById(lesson.getId());
                });
    }

    @Override
    public List<LessonEntity> findAllByModuleAndSortAscBySerialNumber(@NotNull ModuleEntity module)
    {
        return crudLessonEntityService.findAllByModuleIdAndSortAscBySerialNumber(module.getId());
    }

    private int getNextSerialNumber(@NotNull ModuleEntity module)
    {
        List<LessonEntity> moduleLessons = findAllByModuleAndSortAscBySerialNumber(module);
        if (moduleLessons.size() == 0)
        {
            return 0;
        }

        return moduleLessons.get(moduleLessons.size() - 1).getSerialNumber() + 1;
    }

    @Override
    public List<LessonWithStagesInfo> findAllLessonsWithStagesByModule(@NotNull ModuleEntity module)
    {
        List<LessonWithStagesInfo> lessonsWithStagesList = new ArrayList<>();

        findAllByModuleAndSortAscBySerialNumber(module).forEach(lesson ->
        {
            LessonWithStagesInfo lessonWithStages = new LessonWithStagesInfo(
                    lesson,
                    stageService.findAllByLessonAndSortAscBySerialNumber(lesson)
            );
            lessonsWithStagesList.add(lessonWithStages);
        });

        return lessonsWithStagesList;
    }

    @Override
    public Optional<LessonEntity> getOptional(@NotNull Long id)
    {
        return crudLessonEntityService.findById(id);
    }

    @Override
    public LessonEntity getOrThrow(@NotNull Long id)
    {
        return crudLessonEntityService.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find lesson with id %s".formatted(id)));
    }

    @Override
    public void update(@NotNull ModuleEntity moduleEntity, @NotNull Long lessonId,
            @NotNull UpdateCourseAuthorLessonDto updateLessonDto)
    {
        LessonEntity lessonEntityFromDb = getOrThrow(lessonId);
        if (lessonEntityFromDb.getSerialNumber() != updateLessonDto.getSerialNumber())
        {
            List<LessonEntity> moduleLessons = findAllByModuleAndSortAscBySerialNumber(moduleEntity);
            LessonEntity lessonEntityFromList = moduleLessons.stream()
                    .filter(lesson -> lesson.getId().equals(lessonId))
                    .findFirst()
                    .orElseThrow();

            int newSerialNumber = updateLessonDto.getSerialNumber();

            for (LessonEntity lesson : moduleLessons)
            {
                if (lesson != lessonEntityFromList)
                {
                    int currentSerialNumber = lesson.getSerialNumber();
                    if (currentSerialNumber >= newSerialNumber
                            && currentSerialNumber < lessonEntityFromDb.getSerialNumber())
                    {
                        lesson.setSerialNumber(currentSerialNumber + 1);
                    }
                    else if (currentSerialNumber > lessonEntityFromDb.getSerialNumber()
                            && currentSerialNumber <= newSerialNumber)
                    {
                        lesson.setSerialNumber(currentSerialNumber - 1);
                    }
                }
            }

            lessonEntityFromList.setTitle(updateLessonDto.getTitle());
            lessonEntityFromList.setSerialNumber(updateLessonDto.getSerialNumber());

            moduleLessons.forEach(crudLessonEntityService::update);
        }

        lessonEntityFromDb.setTitle(updateLessonDto.getTitle());
        crudLessonEntityService.update(lessonEntityFromDb);
    }

    @Override
    public void delete(@NotNull ModuleEntity moduleEntity, @NotNull Long lessonId)
    {
        List<LessonEntity> moduleLessons = findAllByModuleAndSortAscBySerialNumber(moduleEntity);
        LessonEntity lessonEntityFromList = moduleLessons.stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .findFirst()
                .orElseThrow();
        moduleLessons.remove(lessonEntityFromList);

        int removedSerialNumber = lessonEntityFromList.getSerialNumber();
        for (LessonEntity lesson : moduleLessons)
        {
            if (lesson.getSerialNumber() > removedSerialNumber)
            {
                lesson.setSerialNumber(lesson.getSerialNumber() - 1);
                crudLessonEntityService.update(lesson);
            }
        }

        stageService.deleteAllByLesson(lessonEntityFromList);
        crudLessonEntityService.deleteById(lessonId);
    }
}
