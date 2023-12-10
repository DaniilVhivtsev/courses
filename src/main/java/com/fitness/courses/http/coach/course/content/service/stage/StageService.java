package com.fitness.courses.http.coach.course.content.service.stage;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.StageEntity;

public interface StageService
{
    List<StageEntity> findAllByLessonAndSortAscBySerialNumber(@NotNull LessonEntity lesson);
}
