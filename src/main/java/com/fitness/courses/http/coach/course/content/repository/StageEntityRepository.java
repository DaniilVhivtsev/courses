package com.fitness.courses.http.coach.course.content.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;

@Repository
public interface StageEntityRepository extends JpaRepository<StageEntity, Long>
{
    List<StageEntity> findAllByLessonIdOrderBySerialNumberAsc(Long lessonId);
}
