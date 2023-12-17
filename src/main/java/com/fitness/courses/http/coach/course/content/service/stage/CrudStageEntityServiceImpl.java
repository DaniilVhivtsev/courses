package com.fitness.courses.http.coach.course.content.service.stage;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;
import com.fitness.courses.http.coach.course.content.repository.StageEntityRepository;

@Service
public class CrudStageEntityServiceImpl implements CrudStageEntityService
{
    private final StageEntityRepository stageEntityRepository;

    @Autowired
    public CrudStageEntityServiceImpl(
            StageEntityRepository stageEntityRepository)
    {
        this.stageEntityRepository = stageEntityRepository;
    }

    @Override
    @Transactional
    public void deleteByLessonEntity(@NotNull LessonEntity lesson)
    {
        stageEntityRepository.deleteAllByLesson(lesson);
    }

    @Override
    @Transactional
    public StageEntity save(@NotNull StageEntity entity)
    {
        return stageEntityRepository.save(entity);
    }

    @Override
    @Transactional
    public StageEntity update(@NotNull StageEntity entity)
    {
        return this.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StageEntity> findById(@NotNull Long id)
    {
        return stageEntityRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public StageEntity findByIdOrThrow(@NotNull Long id) throws NotFoundException
    {
        return this.findById(id).orElseThrow(() -> new NotFoundException("Can't find stage by id %s".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StageEntity> findAllByLessonIdAndSortAscBySerialNumber(@NotNull Long lessonId)
    {
        return stageEntityRepository.findAllByLessonIdOrderBySerialNumberAsc(lessonId);
    }

    @Override
    @Transactional
    public void deleteById(@NotNull Long id)
    {
        stageEntityRepository.deleteById(id);
    }
}
