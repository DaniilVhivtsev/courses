package com.fitness.courses.http.student.variable.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.student.variable.model.entity.StudentVariableEntity;

@Repository
public interface StudentVariableRepository extends JpaRepository<StudentVariableEntity, Long>
{
    void removeAllByCourseVariableId(Long courseVariableId);

    Optional<StudentVariableEntity> findAllByCourseVariableId(Long courseVariableId);
}
