package com.fitness.courses.http.coach.course.content.service.stage;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.StageEntity;

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
    public List<StageEntity> findAllByLessonAndSortAscBySerialNumber(@NotNull LessonEntity lesson)
    {
        return crudStageEntityService.findAllByLessonIdAndSortAscBySerialNumber(lesson.getId());
    }
}
