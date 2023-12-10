package com.fitness.courses.http.coach.course.content.service.stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.NotFoundException;
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
}
