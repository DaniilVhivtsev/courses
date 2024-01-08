package com.fitness.courses.http.coach.course.content.service.lesson;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.course.content.model.dto.lesson.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.lesson.UpdateCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.model.info.LessonWithStagesInfo;

public interface LessonService
{
    LessonEntity add(@NotNull ModuleEntity moduleEntity, @NotNull NewCourseAuthorLessonDto newLessonDto);

    void deleteAllByModule(@NotNull ModuleEntity moduleEntity);

    List<LessonEntity> findAllByModuleAndSortAscBySerialNumber(@NotNull ModuleEntity module);

    List<LessonWithStagesInfo> findAllLessonsWithStagesByModule(@NotNull ModuleEntity module);

    Optional<LessonEntity> getOptional(@NotNull Long id);

    LessonEntity getOrThrow(@NotNull Long id);

    void update(ModuleEntity moduleEntity, Long lessonId, UpdateCourseAuthorLessonDto updateLessonDto);

    void delete(@NotNull ModuleEntity moduleEntity, @NotNull Long lessonId);
}
