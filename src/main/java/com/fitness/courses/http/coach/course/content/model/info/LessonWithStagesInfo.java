package com.fitness.courses.http.coach.course.content.model.info;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.StageEntity;

public record LessonWithStagesInfo(LessonEntity lesson, List<StageEntity> stages)
{
}
