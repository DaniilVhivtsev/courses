package com.fitness.courses.http.coach.course.content.service.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.content.model.dto.NewCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.UpdateCourseAuthorModuleDto;
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
    public void add(CourseEntity courseEntity, NewCourseAuthorModuleDto newModuleDto)
    {
        ModuleEntity newModuleEntity = new ModuleEntity();
        newModuleEntity.setTitle(newModuleDto.getTitle());
        newModuleEntity.setDescription(newModuleDto.getDescription());
        newModuleEntity.setSerialNumber(getNextSerialNumber(courseEntity));
        newModuleEntity.setCourse(courseEntity);

        crudModuleEntityService.save(newModuleEntity);
    }

    @Override
    public List<ModuleEntity> findAllByCourseAndSortAscBySerialNumber(@NotNull CourseEntity course)
    {
        return crudModuleEntityService.findAllByCourseIdAndSortAscBySerialNumber(course.getId());
    }

    private int getNextSerialNumber(@NotNull CourseEntity course)
    {
        List<ModuleEntity> courseModules = findAllByCourseAndSortAscBySerialNumber(course);
        if (courseModules.size() == 0)
        {
            return 0;
        }

        return courseModules.get(courseModules.size() - 1).getSerialNumber() + 1;
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

    @Override
    public Optional<ModuleEntity> getOptional(@NotNull Long id)
    {
        return crudModuleEntityService.findById(id);
    }

    @Override
    public ModuleEntity getOrThrow(@NotNull Long id)
    {
        return crudModuleEntityService.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find module with id %s".formatted(id)));
    }

    @Override
    public void update(@NotNull CourseEntity courseEntityFromDb, @NotNull Long moduleId,
            @NotNull UpdateCourseAuthorModuleDto updateModuleDto)
    {
        ModuleEntity moduleEntityFromDb = getOrThrow(moduleId);
        if (moduleEntityFromDb.getSerialNumber() != updateModuleDto.getSerialNumber())
        {
            List<ModuleEntity> courseModules = findAllByCourseAndSortAscBySerialNumber(courseEntityFromDb);
            ModuleEntity moduleEntityFromList = courseModules.stream()
                    .filter(module -> module.getId().equals(moduleId))
                    .findFirst()
                    .orElseThrow();

            moduleEntityFromList.setSerialNumber(updateModuleDto.getSerialNumber());
            moduleEntityFromList.setTitle(updateModuleDto.getTitle());
            moduleEntityFromList.setDescription(updateModuleDto.getDescription());

            int newSerialNumber = updateModuleDto.getSerialNumber();

            for (ModuleEntity module : courseModules)
            {
                if (module != moduleEntityFromList)
                {
                    int currentSerialNumber = module.getSerialNumber();
                    if (currentSerialNumber >= newSerialNumber && currentSerialNumber < moduleEntityFromDb.getSerialNumber())
                    {
                        module.setSerialNumber(currentSerialNumber + 1);
                    }
                    else if (currentSerialNumber > moduleEntityFromDb.getSerialNumber() && currentSerialNumber <= newSerialNumber)
                    {
                        module.setSerialNumber(currentSerialNumber - 1);
                    }
                }
            }

            courseModules.forEach(crudModuleEntityService::update);
        }

        moduleEntityFromDb.setTitle(updateModuleDto.getTitle());
        moduleEntityFromDb.setDescription(updateModuleDto.getDescription());
        crudModuleEntityService.update(moduleEntityFromDb);
    }
}
