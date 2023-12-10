package com.fitness.courses.http.coach.course.content.service.module;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;
import com.fitness.courses.http.coach.course.content.repository.ModuleEntityRepository;

@Service
public class CrudModuleEntityServiceImpl implements CrudModuleEntityService
{
    private final ModuleEntityRepository moduleEntityRepository;

    @Autowired
    public CrudModuleEntityServiceImpl(
            ModuleEntityRepository moduleEntityRepository)
    {
        this.moduleEntityRepository = moduleEntityRepository;
    }

    @Override
    @Transactional
    public ModuleEntity save(@NotNull ModuleEntity entity)
    {
        return moduleEntityRepository.save(entity);
    }

    @Override
    @Transactional
    public ModuleEntity update(@NotNull ModuleEntity entity)
    {
        return this.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModuleEntity> findById(@NotNull Long id)
    {
        return moduleEntityRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleEntity findByIdOrThrow(@NotNull Long id) throws NotFoundException
    {
        return this.findById(id).orElseThrow(() -> new NotFoundException("Can't find module by id %s".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleEntity> findAllByCourseIdAndSortAscBySerialNumber(@NotNull Long courseId)
    {
        return moduleEntityRepository.findAllByCourseIdOrderBySerialNumberAsc(courseId);
    }
}
