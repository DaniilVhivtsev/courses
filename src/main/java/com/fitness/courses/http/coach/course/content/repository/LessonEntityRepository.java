package com.fitness.courses.http.coach.course.content.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;

@Repository
public interface LessonEntityRepository extends JpaRepository<LessonEntity, Long>
{
    List<LessonEntity> findAllByModuleIdOrderBySerialNumberAsc(Long moduleId);
}
