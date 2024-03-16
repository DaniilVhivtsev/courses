package com.fitness.courses.http.coach.variable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.coach.variable.model.entity.CourseVariableEntity;

@Repository
public interface CourseVariableRepository extends JpaRepository<CourseVariableEntity, Long>
{
    List<CourseVariableEntity> findAllByCourseId(Long courseId);
}
