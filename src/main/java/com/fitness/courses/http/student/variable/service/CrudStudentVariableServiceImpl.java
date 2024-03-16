package com.fitness.courses.http.student.variable.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.service.crud.AbstractCrudService;
import com.fitness.courses.http.student.variable.model.entity.StudentVariableEntity;
import com.fitness.courses.http.student.variable.repository.StudentVariableRepository;

@Service
public class CrudStudentVariableServiceImpl extends AbstractCrudService<StudentVariableEntity,
        StudentVariableRepository> implements CrudStudentVariableService
{
    @Autowired
    public CrudStudentVariableServiceImpl(StudentVariableRepository repository)
    {
        super(repository, StudentVariableEntity.class);
    }

    @Override
    public Optional<StudentVariableEntity> findAllByCourseVariableId(Long courseVariableId)
    {
        return repository.findAllByCourseVariableId(courseVariableId);
    }

    @Override
    public void removeAllByCourseVariableId(@NotNull Long courseVariableId)
    {
        repository.removeAllByCourseVariableId(courseVariableId);
    }
}
