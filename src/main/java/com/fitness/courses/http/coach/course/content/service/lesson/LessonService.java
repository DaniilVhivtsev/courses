package com.fitness.courses.http.coach.course.content.service.lesson;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.course.content.model.dto.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.model.info.LessonWithStagesInfo;

public interface LessonService
{
    void add(@NotNull ModuleEntity moduleEntity, @NotNull NewCourseAuthorLessonDto newLessonDto);

    List<LessonEntity> findAllByModuleAndSortAscBySerialNumber(@NotNull ModuleEntity module);

    List<LessonWithStagesInfo> findAllLessonsWithStagesByModule(@NotNull ModuleEntity module);

    Optional<LessonEntity> getOptional(@NotNull Long id);
}
