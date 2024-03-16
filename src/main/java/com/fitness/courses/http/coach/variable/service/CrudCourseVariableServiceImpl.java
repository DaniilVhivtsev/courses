package com.fitness.courses.http.coach.variable.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.service.crud.AbstractCrudService;
import com.fitness.courses.http.coach.variable.model.entity.CourseVariableEntity;
import com.fitness.courses.http.coach.variable.repository.CourseVariableRepository;

@Service
public class CrudCourseVariableServiceImpl extends AbstractCrudService<CourseVariableEntity, CourseVariableRepository>
        implements CrudCourseVariableService
{
    @Autowired
    public CrudCourseVariableServiceImpl(CourseVariableRepository repository)
    {
        super(repository, CourseVariableEntity.class);
    }

    @Override
    public List<CourseVariableEntity> findAllByCourseId(Long courseId)
    {
        return repository.findAllByCourseId(courseId);
    }
}
