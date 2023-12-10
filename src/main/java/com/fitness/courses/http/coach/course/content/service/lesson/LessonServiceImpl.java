package com.fitness.courses.http.coach.course.content.service.lesson;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.model.info.LessonWithStagesInfo;
import com.fitness.courses.http.coach.course.content.service.stage.StageService;

@Service
public class LessonServiceImpl implements LessonService
{
    private final CrudLessonEntityService crudLessonEntityService;
    private final StageService stageService;

    @Autowired
    public LessonServiceImpl(
            CrudLessonEntityService crudLessonEntityService,
            StageService stageService)
    {
        this.crudLessonEntityService = crudLessonEntityService;
        this.stageService = stageService;
    }

    @Override
    public List<LessonEntity> findAllByModuleAndSortAscBySerialNumber(@NotNull ModuleEntity module)
    {
        return crudLessonEntityService.findAllByModuleIdAndSortAscBySerialNumber(module.getId());
    }

    @Override
    public List<LessonWithStagesInfo> findAllLessonsWithStagesByModule(@NotNull ModuleEntity module)
    {
        List<LessonWithStagesInfo> lessonsWithStagesList = new ArrayList<>();

        findAllByModuleAndSortAscBySerialNumber(module).forEach(lesson -> {
            LessonWithStagesInfo lessonWithStages = new LessonWithStagesInfo(
                    lesson,
                    stageService.findAllByLessonAndSortAscBySerialNumber(lesson)
            );
            lessonsWithStagesList.add(lessonWithStages);
        });

        return lessonsWithStagesList;
    }
}
