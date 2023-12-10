package com.fitness.courses.http.coach.course.content.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.coach.course.content.model.entity.ModuleEntity;

@Repository
public interface ModuleEntityRepository extends JpaRepository<ModuleEntity, Long>
{
    List<ModuleEntity> findAllByCourseIdOrderBySerialNumberAsc(Long courseId);
}
