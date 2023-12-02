package com.fitness.courses.http.coach.course.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.model.CourseEntity;
import com.fitness.courses.http.coach.course.repository.CourseEntityRepository;

@Service
public class CrudCourseEntityServiceImpl implements CrudCourseEntityService
{
    private final CourseEntityRepository courseEntityRepository;

    @Autowired
    public CrudCourseEntityServiceImpl(
            CourseEntityRepository courseEntityRepository)
    {
        this.courseEntityRepository = courseEntityRepository;
    }

    @Override
    @Transactional
    public CourseEntity save(@NotNull CourseEntity courseEntity)
    {
        return courseEntityRepository.save(courseEntity);
    }

    @Override
    @Transactional
    public CourseEntity update(@NotNull CourseEntity courseEntity)
    {
        return this.save(courseEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseEntity> findById(@NotNull Long id)
    {
        return courseEntityRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseEntity findByIdOrThrow(@NotNull Long id) throws NotFoundException
    {
        return this.findById(id).orElseThrow(() -> new NotFoundException("Can't find user by id %s".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseEntity> findByAuthorId(@NotNull Long authorId)
    {
        return courseEntityRepository.findByAuthorId(authorId);
    }
}
