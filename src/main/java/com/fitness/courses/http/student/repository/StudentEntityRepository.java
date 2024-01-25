package com.fitness.courses.http.student.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.courses.http.student.model.entity.StudentEntity;

@Repository
public interface StudentEntityRepository extends JpaRepository<StudentEntity, Long>
{
    List<StudentEntity> findAllByCourseId(Long courseId);

    int countByCourseId(Long courseId);

    Optional<StudentEntity> findFirstByUserIdAndCourseId(Long userId, Long courseId);

    List<StudentEntity> findAllByUserIdOrderById(Long userId);
}
