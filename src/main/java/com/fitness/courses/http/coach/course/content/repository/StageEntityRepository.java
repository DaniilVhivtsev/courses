package com.fitness.courses.http.coach.course.content.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.AbstractStageContent;

@Repository
public interface StageEntityRepository extends JpaRepository<StageEntity, Long>
{
    List<StageEntity> findAllByLessonIdOrderBySerialNumberAsc(Long lessonId);

    void deleteAllByLesson(LessonEntity lessonEntity);

    @Modifying
    @Query("update StageEntity t set t.stageContent = :value where t.id = :id")
    int setValue(@Param("id") Long id, @Param("value") String value);
}
