package com.fitness.courses.http.student.variable.service;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.student.variable.model.entity.StudentVariableEntity;

public interface StudentVariableService
{
    StudentVariableEntity addNew(StudentVariableEntity newStudentVariable);

    StudentVariableEntity update(StudentVariableEntity updatedStudentVariable);

    Optional<StudentVariableEntity> findAllByCourseVariableIdAndStudentId(Long courseVariableId, Long studentId);

    void removeAllByCourseVariableId(@NotNull Long courseVariableId);
}
