package com.fitness.courses.http.coach.course.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.model.info.CourseEntityWithStudentsCount;
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
    public void deleteById(@NotNull Long courseId)
    {
        courseEntityRepository.deleteById(courseId);
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
        return this.findById(id).orElseThrow(() -> new NotFoundException("Can't find course by id %s".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseEntity> findByAuthorId(@NotNull Long authorId)
    {
        return courseEntityRepository.findByAuthorId(authorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseEntity> findAllSortByTimeCreatedDesc(@NotNull Integer offset, @NotNull Integer limit)
    {
        final Pageable pageable = PageRequest.of(offset, limit);
        return courseEntityRepository.findAllByOrderByDateTimeCreatedDesc(pageable)
                .stream().toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseEntityWithStudentsCount> findAllSortByStudentsCount(@NotNull Integer offset,
            @NotNull Integer limit)
    {
        final Pageable pageable = PageRequest.of(offset, limit);
        return courseEntityRepository.findCoursesByPopularity(pageable)
                .stream()
                .map(objects -> new CourseEntityWithStudentsCount((CourseEntity)objects[0], (Integer)objects[1]))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseEntity> findAllByKeyword(@NotNull String keyword, @NotNull Integer offset,
            @NotNull Integer limit)
    {
        final Pageable pageable = PageRequest.of(offset, limit);
        return courseEntityRepository.findCoursesByKeyword(keyword, pageable).stream()
                .toList();
    }
}
