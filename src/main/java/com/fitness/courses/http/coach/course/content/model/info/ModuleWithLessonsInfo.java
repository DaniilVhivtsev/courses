package com.fitness.courses.http.coach.course.content.model.info;

import java.util.List;

import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;

public record ModuleWithLessonsInfo(ModuleEntity module, List<LessonEntity> lessons)
{
}
