package com.fitness.courses.http.coach.course.content.service.module;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;

public interface CrudModuleEntityService
{
    @Transactional
    ModuleEntity save(@NotNull ModuleEntity entity);

    @Transactional
    ModuleEntity update(@NotNull ModuleEntity entity);

    @Transactional(readOnly = true)
    Optional<ModuleEntity> findById(@NotNull Long id);

    @Transactional(readOnly = true)
    ModuleEntity findByIdOrThrow(@NotNull Long id) throws NotFoundException;

    @Transactional(readOnly = true)
    List<ModuleEntity> findAllByCourseIdAndSortAscBySerialNumber(@NotNull Long courseId);
}
