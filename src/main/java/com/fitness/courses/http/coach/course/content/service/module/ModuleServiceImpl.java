package com.fitness.courses.http.coach.course.content.service.module;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.model.info.ModuleWithLessonsInfo;
import com.fitness.courses.http.coach.course.content.service.lesson.LessonService;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;

@Service
public class ModuleServiceImpl implements ModuleService
{
    private final CrudModuleEntityService crudModuleEntityService;
    private final LessonService lessonService;

    @Autowired
    public ModuleServiceImpl(
            CrudModuleEntityService crudModuleEntityService,
            LessonService lessonService)
    {
        this.crudModuleEntityService = crudModuleEntityService;
        this.lessonService = lessonService;
    }

    @Override
    public List<ModuleEntity> findAllByCourseAndSortAscBySerialNumber(@NotNull CourseEntity course)
    {
        return crudModuleEntityService.findAllByCourseIdAndSortAscBySerialNumber(course.getId());
    }

    @Override
    public List<ModuleWithLessonsInfo> findAllModulesWithLessonsByCourse(@NotNull CourseEntity course)
    {
        List<ModuleWithLessonsInfo> modulesWithLessonsList = new ArrayList<>();

        findAllByCourseAndSortAscBySerialNumber(course).forEach(module -> {
            ModuleWithLessonsInfo moduleWithLessons = new ModuleWithLessonsInfo(
                    module,
                    lessonService.findAllByModuleAndSortAscBySerialNumber(module)
            );
            modulesWithLessonsList.add(moduleWithLessons);
        });

        return modulesWithLessonsList;
    }
}
