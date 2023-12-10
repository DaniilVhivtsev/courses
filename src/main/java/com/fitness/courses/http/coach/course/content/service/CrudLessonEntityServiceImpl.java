package com.fitness.courses.http.coach.course.content.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.repository.LessonEntityRepository;

@Service
public class CrudLessonEntityServiceImpl implements CrudLessonEntityService
{
    private final LessonEntityRepository lessonEntityRepository;

    @Autowired
    public CrudLessonEntityServiceImpl(
            LessonEntityRepository lessonEntityRepository)
    {
        this.lessonEntityRepository = lessonEntityRepository;
    }

    @Override
    @Transactional
    public LessonEntity save(@NotNull LessonEntity entity)
    {
        return lessonEntityRepository.save(entity);
    }

    @Override
    @Transactional
    public LessonEntity update(@NotNull LessonEntity entity)
    {
        return this.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LessonEntity> findById(@NotNull Long id)
    {
        return lessonEntityRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public LessonEntity findByIdOrThrow(@NotNull Long id) throws NotFoundException
    {
        return this.findById(id).orElseThrow(() -> new NotFoundException("Can't find lesson by id %s".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LessonEntity> findAllByModuleIdAndSortAscBySerialNumber(@NotNull Long moduleId)
    {
        return lessonEntityRepository.findAllByModuleIdOrderBySerialNumberAsc(moduleId);
    }
}
