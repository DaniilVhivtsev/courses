package com.fitness.courses.http.student.variable.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.student.variable.model.entity.StudentVariableEntity;

@Service
public class StudentVariableServiceImpl implements StudentVariableService
{
    private final CrudStudentVariableServiceImpl crudStudentVariableService;

    @Autowired
    public StudentVariableServiceImpl(
            CrudStudentVariableServiceImpl crudStudentVariableService)
    {
        this.crudStudentVariableService = crudStudentVariableService;
    }

    @Override
    public StudentVariableEntity addNew(StudentVariableEntity newStudentVariable)
    {
        return crudStudentVariableService.save(newStudentVariable);
    }

    @Override
    public StudentVariableEntity update(StudentVariableEntity updatedStudentVariable)
    {
        return crudStudentVariableService.update(updatedStudentVariable);
    }

    @Override
    public Optional<StudentVariableEntity> findAllByCourseVariableId(Long courseVariableId)
    {
        return crudStudentVariableService.findAllByCourseVariableId(courseVariableId);
    }

    @Override
    public void removeAllByCourseVariableId(Long courseVariableId)
    {
        crudStudentVariableService.removeAllByCourseVariableId(courseVariableId);
    }
}
