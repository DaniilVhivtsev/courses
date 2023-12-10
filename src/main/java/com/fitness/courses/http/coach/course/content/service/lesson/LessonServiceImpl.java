package com.fitness.courses.http.coach.course.content.service.lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.coach.course.content.model.dto.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.NewCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.model.info.LessonWithStagesInfo;
import com.fitness.courses.http.coach.course.content.service.stage.StageService;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

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
    public void add(@NotNull ModuleEntity moduleEntity, @NotNull NewCourseAuthorLessonDto newLessonDto)
    {
        LessonEntity newLessonEntity = new LessonEntity();
        newLessonEntity.setTitle(newLessonDto.getTitle());
        newLessonEntity.setSerialNumber(getNextSerialNumber(moduleEntity));
        newLessonEntity.setModule(moduleEntity);

        crudLessonEntityService.save(newLessonEntity);
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
}
