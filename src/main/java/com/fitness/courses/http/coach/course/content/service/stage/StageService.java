package com.fitness.courses.http.coach.course.content.service.stage;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.course.content.model.dto.stage.AddCourseAuthorStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.UpdateCourseAuthorStageDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateAbstractStageContentDto;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;

public interface StageService
{
    void add(@NotNull LessonEntity lesson);

    void addContent(@NotNull Long stageId, @NotNull AddCourseAuthorStageContentInfoDto addContentDto);

    void deleteAllByLesson(@NotNull LessonEntity lesson);

    List<StageEntity> findAllByLessonAndSortAscBySerialNumber(@NotNull LessonEntity lesson);

    Optional<StageEntity> getOptional(@NotNull Long id);

    StageEntity getOrThrow(@NotNull Long id);

    void update(@NotNull LessonEntity lessonEntityFromDb, @NotNull Long stageId,
            @NotNull UpdateCourseAuthorStageDto updateStageDto);

    void delete(@NotNull LessonEntity lessonEntityFromDb, @NotNull Long stageId);

    void updateStageContent(@NotNull Long stageId,
            @NotNull UpdateAbstractStageContentDto updateAbstractStageContentDto);
}
