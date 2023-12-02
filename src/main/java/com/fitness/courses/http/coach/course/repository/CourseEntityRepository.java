package com.fitness.courses.http.coach.course.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.coach.course.model.CourseEntity;

@Repository
public interface CourseEntityRepository extends JpaRepository<CourseEntity, Long>
{
    List<CourseEntity> findByAuthorId(Long authorId);
}
