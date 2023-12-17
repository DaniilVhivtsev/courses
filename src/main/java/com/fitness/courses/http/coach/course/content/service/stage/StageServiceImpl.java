package com.fitness.courses.http.coach.course.content.service.stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.content.model.dto.stage.UpdateCourseAuthorStageDto;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;

@Service
public class StageServiceImpl implements StageService
{
    private final CrudStageEntityService crudStageEntityService;

    @Autowired
    public StageServiceImpl(
            CrudStageEntityService crudStageEntityService)
    {
        this.crudStageEntityService = crudStageEntityService;
    }

    @Override
    public void add(@NotNull LessonEntity lesson)
    {
        StageEntity newStageEntity = new StageEntity();
        newStageEntity.setStageContent(new ArrayList<>());
        newStageEntity.setSerialNumber(getNextSerialNumber(lesson));
        newStageEntity.setLesson(lesson);

        crudStageEntityService.save(newStageEntity);
    }

    @Override
    public void deleteAllByLesson(LessonEntity lesson)
    {
        crudStageEntityService.deleteByLessonEntity(lesson);
    }

    @Override
    public List<StageEntity> findAllByLessonAndSortAscBySerialNumber(@NotNull LessonEntity lesson)
    {
        return crudStageEntityService.findAllByLessonIdAndSortAscBySerialNumber(lesson.getId());
    }

    private int getNextSerialNumber(@NotNull LessonEntity lesson)
    {
        List<StageEntity> lessonStages = findAllByLessonAndSortAscBySerialNumber(lesson);
        if (lessonStages.size() == 0)
        {
            return 0;
        }

        return lessonStages.get(lessonStages.size() - 1).getSerialNumber() + 1;
    }

    @Override
    public Optional<StageEntity> getOptional(@NotNull Long id)
    {
        return crudStageEntityService.findById(id);
    }

    @Override
    public StageEntity getOrThrow(@NotNull Long id)
    {
        return crudStageEntityService.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find stage with id %s".formatted(id)));
    }

    @Override
    public void update(@NotNull LessonEntity lessonEntityFromDb, @NotNull Long stageId,
            @NotNull UpdateCourseAuthorStageDto updateStageDto)
    {
        StageEntity stageEntityFromDb = getOrThrow(stageId);
        if (stageEntityFromDb.getSerialNumber() != updateStageDto.getSerialNumber())
        {
            List<StageEntity> lessonStages = findAllByLessonAndSortAscBySerialNumber(lessonEntityFromDb);
            StageEntity stageEntityFromList = lessonStages.stream()
                    .filter(stage -> stage.getId().equals(stageId))
                    .findFirst()
                    .orElseThrow();

            int newSerialNumber = updateStageDto.getSerialNumber();

            for (StageEntity stage : lessonStages)
            {
                if (stage != stageEntityFromList)
                {
                    int currentSerialNumber = stage.getSerialNumber();
                    if (currentSerialNumber >= newSerialNumber
                            && currentSerialNumber < lessonEntityFromDb.getSerialNumber())
                    {
                        stage.setSerialNumber(currentSerialNumber + 1);
                    }
                    else if (currentSerialNumber > lessonEntityFromDb.getSerialNumber()
                            && currentSerialNumber <= newSerialNumber)
                    {
                        stage.setSerialNumber(currentSerialNumber - 1);
                    }
                }
            }

            stageEntityFromList.setSerialNumber(updateStageDto.getSerialNumber());

            lessonStages.forEach(crudStageEntityService::update);
        }
    }

    @Override
    public void delete(@NotNull LessonEntity lessonEntityFromDb, @NotNull Long stageId)
    {
        List<StageEntity> lessonStages = findAllByLessonAndSortAscBySerialNumber(lessonEntityFromDb);
        StageEntity stageEntityFromList = lessonStages.stream()
                .filter(stage -> stage.getId().equals(stageId))
                .findFirst()
                .orElseThrow();

        lessonStages.remove(stageEntityFromList);

        int removedSerialNumber = stageEntityFromList.getSerialNumber();
        for (StageEntity stage : lessonStages) {
            if (stage.getSerialNumber() > removedSerialNumber) {
                stage.setSerialNumber(stage.getSerialNumber() - 1);
                crudStageEntityService.update(stage);
            }
        }

        crudStageEntityService.deleteById(stageId);
    }
}
